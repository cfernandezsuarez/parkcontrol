package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlazaService {

    private IPlazaRepository repository;

    public Page<Plaza> consultarPlazas(Integer planta, TipoPlaza tipoPlaza, EstadoPlaza estadoPlaza) {
        return repository.findAllByPlantaAndTipoAndEstado(planta, tipoPlaza, estadoPlaza, PageRequest.of(0, 10));
    }

    public List<Plaza> consultarPlazasDisponibles(Integer planta, TipoPlaza tipoPlaza) {
        return repository.plazasLibresPorPlantaTipo(planta, tipoPlaza);
    }

}
