package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.exceptions.ConflictoMatricula;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private ITicketRepository repository;
    private ITarifaRepositorio tarifaRepository;
    private IPlazaRepository plazaRepository;

    public String registrarEntrada(TicketEntradaRequest request) {
        String matricula = request.matricula().toUpperCase();
        LocalDateTime fechaEntrada = LocalDateTime.now();

        if (repository.existsByMatriculaAndEstado(matricula, EstadoTicket.ABIERTO)) {
            throw new ConflictoMatricula("La matrícula ya tiene un ticket activo");
        }

        Tarifa tarifa = tarifaRepository.findTarifaVigente(fechaEntrada)
                .orElseThrow(() -> new ReglaNegocio("No hay tarifa vigente"));

        TipoPlaza tipo = request.tipoPlaza() != null ? request.tipoPlaza() : TipoPlaza.NORMAL;

        List<Plaza> plazasLibres = plazaRepository.plazasLibresPorTipo(
                EstadoPlaza.LIBRE, tipo, PageRequest.of(0, 1)
        );

        if (plazasLibres.isEmpty()) {
            throw new ReglaNegocio("No hay plazas libres del tipo " + tipo);
        }

        Plaza plaza = plazasLibres.get(0);

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

}
