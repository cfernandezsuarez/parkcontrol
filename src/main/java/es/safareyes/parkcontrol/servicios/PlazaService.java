package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.OcupacionPorPlanta;
import es.safareyes.parkcontrol.dtos.TotalPlazasOcupadas;
import es.safareyes.parkcontrol.exceptions.ElementoNoEncontrado;
import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlazaService {

    private IPlazaRepository repository;

    public Plaza consultarPlazaPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ElementoNoEncontrado("No se encuentra la plaza"));
    }

    public Long consultarPlazasTotales() {
        return repository.count();
    }

    public List<TotalPlazasOcupadas> consultarPlazasTotalesOcupadas() {
        return repository.totalPlazasOcupadas();
    }

    public Page<Plaza> consultarPlazas(Integer planta, TipoPlaza tipoPlaza, EstadoPlaza estadoPlaza) {
        return repository.findAllByPlantaAndTipoAndEstado(planta, tipoPlaza, estadoPlaza, PageRequest.of(0, 10));
    }

    public OcupacionPorPlanta consultarOcupacionPorPlanta() {
        return repository.ocupacionPorPlanta();
    }

    public List<Plaza> consultarPlazasDisponibles(Integer planta, TipoPlaza tipoPlaza) {
        return repository.plazasLibresPorPlantaTipo(planta, tipoPlaza);
    }

    public Long consultarPlazasPorPlantaMaxima(Integer planta) {
        return repository.countByPlanta(planta);
    }

    public Long consultarPlazasEnMantenimiento() {
        return repository.countByEstado(EstadoPlaza.BLOQUEADA);
    }

    public void crearNuevaPlaza(CrearNuevaPlazaDTO dto) {
        Plaza plaza = new Plaza();
        plaza.setNumero(dto.numero());
        plaza.setPlanta(dto.planta());
        plaza.setTipo(dto.tipo());
        plaza.setEstado(dto.estado());
        repository.save(plaza);
    }

    public void bloquearPlaza(Integer id) {
        Plaza plaza = repository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontrado("No se encuentra la plaza"));
        EstadoPlaza estadoBloqueado = EstadoPlaza.BLOQUEADA;
        EstadoPlaza estadoLibre = EstadoPlaza.LIBRE;
        if (plaza.getEstado().equals(estadoBloqueado)) {
            plaza.setEstado(estadoLibre);
        } else {
            plaza.setEstado(estadoBloqueado);
        }
        repository.save(plaza);
    }

}
