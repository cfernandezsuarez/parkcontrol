package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.TarifaMapper;
import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TarifaService {

    private ITarifaRepositorio repository;
    private TarifaMapper mapper;

    public Tarifa tarifasVigor() {
        return repository.tarifaVigente(LocalDateTime.now()).orElseThrow(() -> new ReglaNegocio("No hay tarifa vigente"));
    }

    public void crearNuevaTarifa(CrearNuevaTarifaDTO dto) {

        Tarifa tarifa = mapper.toEntity(dto);

        repository.save(tarifa);
    }
}
