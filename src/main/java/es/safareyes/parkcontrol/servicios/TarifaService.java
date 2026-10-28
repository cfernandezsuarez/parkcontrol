package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TarifaService {

    private ITarifaRepositorio repository;

    public List<Tarifa> tarifasVigor() {
        return repository.tarifaVigor();
    }
}
