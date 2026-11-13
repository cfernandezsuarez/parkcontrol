package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.response.TicketAbonadoResponse;
import es.safareyes.parkcontrol.exceptions.ConflictoMatricula;
import es.safareyes.parkcontrol.exceptions.ElementoNoEncontrado;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private ITicketRepository repository;
    private ITarifaRepositorio tarifaRepository;
    private IPlazaRepository plazaRepository;
    private IUsuarioRepository usuarioRepository;

    public String registrarEntrada(TicketEntradaRequest request) {
        String matricula = request.matricula().toUpperCase();
        LocalDateTime fechaEntrada = LocalDateTime.now();

        if (repository.existsByMatriculaAndEstado(matricula, EstadoTicket.ABIERTO)) {
            throw new ConflictoMatricula("La matrícula ya tiene un ticket activo");
        }

        Tarifa tarifa = tarifaRepository.findFirstByOrderByFechaDesc()
                .orElseThrow(() -> new ReglaNegocio("No hay tarifa vigente"));

        TipoPlaza tipo = request.tipoPlaza() != null ? request.tipoPlaza() : TipoPlaza.NORMAL;

        List<Plaza> plazasLibres = plazaRepository.plazasLibresPorTipo(
                EstadoPlaza.LIBRE, tipo, PageRequest.of(0, 1)
        );

        if (plazasLibres.isEmpty()) {
            throw new ReglaNegocio("No hay plazas libres del tipo " + tipo);
        }

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
