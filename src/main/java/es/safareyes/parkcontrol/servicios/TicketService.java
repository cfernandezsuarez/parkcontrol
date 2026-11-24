package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.TicketMapper;
import es.safareyes.parkcontrol.dtos.request.AplicarDescuentoRequest;
import es.safareyes.parkcontrol.dtos.request.PagarTicketRequest;
import es.safareyes.parkcontrol.dtos.response.*;
import es.safareyes.parkcontrol.exceptions.*;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.*;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private ITicketRepository repository;
    private TarifaService tarifaService;
    private IPlazaRepository plazaRepository;
    private IUsuarioRepository usuarioRepository;
    private IPromocionRepository promocionRepository;
    private IAbonadoRepository abonadoRepository;

    private TicketMapper ticketMapper;

    @Transactional
    public String registrarEntrada(TicketEntradaRequest request) {
        String matricula = request.matricula().toUpperCase();
        LocalDateTime fechaEntrada = LocalDateTime.now();

        // RN-01
        this.validarMatricula(request.matricula().toUpperCase());
        if (repository.existsByMatriculaAndEstado(matricula, EstadoTicket.ABIERTO)) {
            throw new ConflictoMatricula("La matrícula ya tiene un ticket activo");
        }

        // RN-04
        Tarifa tarifa = tarifaService.tarifasVigor();

        // RN-02
        TipoPlaza tipo = request.tipoPlaza() != null ? request.tipoPlaza() : TipoPlaza.NORMAL;
        List<Plaza> plazasLibres = plazaRepository.plazasLibresPorTipo(
                EstadoPlaza.LIBRE, tipo, PageRequest.of(0, 1)
        );
        if (plazasLibres.isEmpty()) {
            throw new SinPlazasLibres("No hay plazas libres del tipo " + tipo);
        }

        // RN-03
        Plaza plaza = plazasLibres.getFirst();
        plaza.setEstado(EstadoPlaza.OCUPADA);
        plazaRepository.save(plaza);

        Ticket ticket = new Ticket();
        ticket.setMatricula(matricula);
        ticket.setHoraEntrada(fechaEntrada);
        ticket.setHoraSalida(null);
        ticket.setMinutosTotales(0);
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setImporte(BigDecimal.ZERO);
        ticket.setDescuento(0);
        ticket.setImporteDescuento(BigDecimal.ZERO);
        ticket.setTarifa(tarifa);
        ticket.setPlaza(plaza);
        repository.save(ticket);

        return "Ticket creado correctamente para la matrícula " + matricula;
    }

    @Transactional(readOnly = true)
    public ImporteTicketResponse importeTicket(String matricula) {

        Ticket ticket = repository.findByMatriculaAndEstado(matricula.toUpperCase(), EstadoTicket.ABIERTO)
                .orElseThrow(() -> new ElementoNoEncontrado("No hay ticket activo para la matrícula " + matricula));

        Tarifa tarifa = ticket.getTarifa();
        LocalDateTime horaActual = LocalDateTime.now();
        CalculoCobro cobro = calcularCobro(ticket, horaActual);

        return new ImporteTicketResponse(
                ticket.getId(),
                ticket.getMatricula(),
                Duration.between(ticket.getHoraEntrada(), horaActual).toMinutes(),
                tarifa.getMinCortesia().longValue(),
                cobro.minutosCobrados(),
                cobro.importe(),
                cobro.esAbonado() ? "Abono vigente: la estancia está cubierta." : "Importe calculado sin cerrar el ticket"
        );

    }

    private void validarMatricula(String matricula) {
        String patron = "^\\d{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$";
        if (matricula == null || !matricula.matches(patron)) {
            throw new MatriculaInvalida("La matrícula no tiene un formato válido (ej: 1234BCD)");
        }
    }

    @Transactional
    public PromocionAplicadaResponse aplicarPromocion(Integer id, AplicarDescuentoRequest request) {
        Ticket ticket = repository.findById(id).orElseThrow(() -> new ElementoNoEncontrado("Ticket no encontrado."));

        // RN-06
        if (ticket.getEstado() != EstadoTicket.ABIERTO) {
            throw new ReglaNegocio("Solo se pueden aplicar promociones a tickets activos.");
        }

        Promocion promocion = promocionRepository.findById(request.idPromocion()).orElseThrow(() -> new ElementoNoEncontrado("No se encuentra la promoción."));

        // RN-06
        if (promocionRepository.existePromocionEnTicket(id, promocion.getId())) {
            throw new PromocionRepetida("La promoción ya está aplicada a este ticket.");
        }

        Integer minutosActuales = repository.minutosDescuentoAcumulados(id);

        Integer nuevosMinutos = minutosActuales + promocion.getMinDescuento();

        // RN-06
        if (nuevosMinutos > 180) {
            throw new ReglaNegocio("No se puede superar los 180 minutos de descuento por ticket.");
        }

        promocion.setTicket(ticket);
        promocionRepository.save(promocion);

        return new PromocionAplicadaResponse(ticket.getId(), promocion.getNombre(), promocion.getMinDescuento(), nuevosMinutos);
    }

    @Transactional(readOnly = true)
    public Page<TicketAbonadoResponse> obtenerMisTickets(String nombreUsuario, Boolean esAbonado, EstadoTicket estado, BigDecimal precioMin, BigDecimal precioMax, Integer minutosMin, Integer minutosMax, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (esAbonado) {
            Integer idAbonado = usuarioRepository.findIdAbonadoByNombre(nombreUsuario);
            if (idAbonado == null) {
                return Page.empty(pageable);
            }

            Abonado abonado = abonadoRepository.findById(idAbonado).orElseThrow(() -> new ElementoNoEncontrado("No se encuentra el abonado"));

            return repository.ticketsPorAbonado(
                    abonado, estado, precioMin, precioMax, minutosMin, minutosMax, pageable
            ).map(t -> ticketMapper.toAbonadoDto(t));

        } else {
            return repository.todosLosTickets(
                    estado, precioMin, precioMax, minutosMin, minutosMax, pageable
            ).map(t -> ticketMapper.toAbonadoDto(t));
        }
    }

    @Transactional
    public TicketPagadoResponse pagarTicket(PagarTicketRequest request) {

        String matricula = request.matricula().toUpperCase();

        Ticket ticket = repository.findByMatriculaAndEstado(matricula, EstadoTicket.ABIERTO)
                .orElseThrow(() -> new ElementoNoEncontrado("No hay ticket activo para la matrícula."));

        LocalDateTime horaActual = LocalDateTime.now();
        CalculoCobro cobro = calcularCobro(ticket, horaActual);

        ticket.setHoraSalida(horaActual);
        ticket.setMinutosTotales(Math.toIntExact(cobro.minutosTotales()));
        ticket.setImporte(cobro.importe());
        ticket.setEstado(EstadoTicket.CERRADO);

        // RN-07
        Plaza plaza = ticket.getPlaza();
        if (plaza != null) {
            plaza.setEstado(EstadoPlaza.LIBRE);
            plazaRepository.save(plaza);
        }

        repository.save(ticket);

        return new TicketPagadoResponse(
                ticket.getId(),
                ticket.getMatricula(),
                ticket.getHoraEntrada(),
                ticket.getHoraSalida(),
                ticket.getMinutosTotales(),
                cobro.minutosDescuento(),
                cobro.importe(),
                plaza != null ? plaza.getNumero() : null
        );
    }

    private CalculoCobro calcularCobro(Ticket ticket, LocalDateTime horaActual) {
        Duration estancia = Duration.between(ticket.getHoraEntrada(), horaActual);
        long minutosTotales = estancia.toMinutes();
        if (estancia.toSecondsPart() > 0) {
            minutosTotales++;
        }

        Tarifa tarifa = ticket.getTarifa();
        int minutosDescuento = repository.minutosDescuentoAcumulados(ticket.getId());
        boolean esAbonado = abonadoRepository.existsByMatriculaAndVigente(ticket.getMatricula(), horaActual);
        long minutosCobrados = esAbonado ? 0
                : Math.max(0, minutosTotales - tarifa.getMinCortesia() - minutosDescuento);
        BigDecimal importe = minutosCobrados == 0 ? BigDecimal.ZERO
                : calcularImporteFinal(minutosTotales, minutosCobrados, tarifa);
        return new CalculoCobro(minutosTotales, minutosDescuento, esAbonado, minutosCobrados, importe);
    }

    private record CalculoCobro(long minutosTotales, int minutosDescuento, boolean esAbonado,
                                long minutosCobrados, BigDecimal importe) {}

    private BigDecimal calcularImporteFinal(long minutos, long minutosCobrados, Tarifa tarifa) {

        BigDecimal importe = BigDecimal.valueOf(minutosCobrados).multiply(tarifa.getPrecio());

        if (tarifa.getTopeMaximo() != null) {
            long dia = (minutos / 1440) +1;
            BigDecimal topeTotal = BigDecimal.valueOf(tarifa.getTopeMaximo()).multiply(BigDecimal.valueOf(dia));

            if (importe.compareTo(topeTotal) > 0) {
                importe = topeTotal;
            }
        }

        double importeRedondeado = Math.round(importe.doubleValue() * 100.0) / 100.0;
        return BigDecimal.valueOf(importeRedondeado);
    }

    @Transactional
    public TicketDetalleResponse obtenerDetalleTicket(Integer idTicket, Authentication authentication) {

        Ticket ticket = repository.findById(idTicket).orElseThrow(() -> new ElementoNoEncontrado("No se encuentra el ticket"));

        Boolean esAbonado = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ABONADO"));

        if (esAbonado) {
            String nombreUsuario = authentication.getName();
            Integer idAbonado = usuarioRepository.findIdAbonadoByNombre(nombreUsuario);

            Boolean esPropietario = ticket.getAbonado().stream()
                    .anyMatch(a -> a.getId().equals(idAbonado));

            if (!esPropietario) {
                throw new ReglaNegocio("No tienes permiso para ver este ticket");
            }
        }

        List<DescuentoDetalleDTO> descuentos = promocionRepository
                .findPromocionesByTicket(idTicket).stream()
                .map(p -> new DescuentoDetalleDTO(p.getNombre(), p.getMinDescuento())).toList();

        return ticketMapper.toDetalleDto(ticket);
    }
}
