package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlazaService {

    private IPlazaRepository repository;

    public List<Plaza> consultarPlazas() {
        return repository.findAllByPlantaAndTipoAndEstado();
    }

    public List<Plaza> consultarPlazasDisponibles() {
        return repository.plazasLibresPorPlantaTipo();
    }

}
