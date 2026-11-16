package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.request.AplicarDescuentoRequest;
import es.safareyes.parkcontrol.dtos.response.ImporteTicketResponse;
import es.safareyes.parkcontrol.dtos.response.TicketAbonadoResponse;
import es.safareyes.parkcontrol.exceptions.*;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @Transactional
    public ImporteTicketResponse importeTicket(String matricula) {

        Ticket ticket = repository.findByMatriculaAndEstado(matricula.toUpperCase(), EstadoTicket.ABIERTO)
                .orElseThrow(() -> new ElementoNoEncontrado("No hay ticket activo para la matrícula " + matricula));

        Tarifa tarifa = ticket.getTarifa();

        BigDecimal importe = calcularImporte(ticket, tarifa);

        long minutos = Duration.between(ticket.getHoraEntrada(), LocalDateTime.now()).toMinutes();

        return new ImporteTicketResponse(
                ticket.getId(),
                ticket.getMatricula(),
                minutos,
                tarifa.getMinCortesia().longValue(),
                Math.max(0, minutos - tarifa.getMinCortesia()),
                importe,
                "Importe calculado sin cerrar el ticket"
        );

    }

    // RN-05
    private BigDecimal calcularImporte(Ticket ticket, Tarifa tarifa) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime entrada = ticket.getHoraEntrada();

        long minutos = Duration.between(entrada, ahora).toMinutes();
        if (Duration.between(entrada, ahora).getSeconds() % 60 > 0) {
            minutos++;
        }

        long cortesia = tarifa.getMinCortesia();

        if (minutos <= cortesia) {
            return BigDecimal.ZERO;
        }

        long minutosCobrados = minutos - cortesia;
        double importeBruto = minutosCobrados * tarifa.getPrecio().doubleValue();

        if (tarifa.getTopeMaximo() != null) {
            long bloquesDe24h = (minutos / 1440) + 1;
            double topeTotal = tarifa.getTopeMaximo() * bloquesDe24h;
            if (importeBruto > topeTotal) {
                importeBruto = topeTotal;
            }
        }

        double importeRedondeado = Math.round(importeBruto * 100.0) / 100.0;
        return BigDecimal.valueOf(importeRedondeado);
    }

    private void validarMatricula(String matricula) {
        String patron = "^\\d{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$";
        if (matricula == null || !matricula.matches(patron)) {
            throw new MatriculaInvalida("La matrícula no tiene un formato válido (ej: 1234BCD)");
        }
    }

    @Transactional
    public String aplicarPromocion(Integer id, AplicarDescuentoRequest request) {

    }

    @Transactional(readOnly = true)
    public List<TicketAbonadoResponse> obtenerMisTickets(String nombreUsuario) {

        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new ElementoNoEncontrado("Usuario no encontrado"));

        return repository.ticketsPorIdUsuario(usuario.getId()).stream()
            .map(t -> new TicketAbonadoResponse(
                t.getId(),
                t.getMatricula(),
                t.getHoraEntrada(),
                t.getHoraSalida(),
                t.getMinutosTotales(),
                t.getImporte(),
                t.getEstado(),
                t.getPlaza() != null ? t.getPlaza().getNumero() : null
            ))
            .toList();
    }
}
