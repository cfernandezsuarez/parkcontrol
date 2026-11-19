package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.EstanciaMediaPlantaDTO;
import es.safareyes.parkcontrol.dtos.response.OcupacionPlantaResponse;
import es.safareyes.parkcontrol.dtos.response.RecaudacionDiaResponse;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InformeService {

    private ITicketRepository repository;
    private IPlazaRepository plazaRepository;
    private ITicketRepository ticketRepository;

    public List<RecaudacionDiaResponse> obtenerRecaudacionDia(LocalDate desde, LocalDate hasta) {

        LocalDateTime horaInicio = desde.atStartOfDay();
        LocalDateTime horaFin = hasta.atTime(23, 59, 59);

        return repository.recaudacionPorDia(horaInicio, horaFin);
    }

    public List<OcupacionPlantaResponse> obtenerOcupacionPorPlanta() {

        List<OcupacionPlantaResponse> ocupacion = plazaRepository.ocupacionPorPlanta();

        List<EstanciaMediaPlantaDTO> estanciaMedia = ticketRepository.estanciaMediaMinutos();

        Map<Integer, Long> mapaEstancias = estanciaMedia.stream()
                .collect(Collectors.toMap(
                        EstanciaMediaPlantaDTO::planta,
                        e -> Math.round(e.estanciaMedia().doubleValue())
                ));


        return ocupacion.stream()
                .map(o -> new OcupacionPlantaResponse(
                        o.planta(),
                        o.ocupadas(),
                        mapaEstancias.getOrDefault(o.planta(), 0L)
                ))
                .toList();
    }

}
