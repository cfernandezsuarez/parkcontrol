package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.response.RecaudacionDiaResponse;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InformeService {

    private ITicketRepository repository;

    public List<RecaudacionDiaResponse> obtenerRecaudacionDia(LocalDate desde, LocalDate hasta) {

        LocalDateTime horaInicio = desde.atStartOfDay();
        LocalDateTime horaFin = hasta.atTime(23, 59, 59);

        return repository.recaudacionPorDia(horaInicio, horaFin);
    }

}
