package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.PlazasOcupacionDTO;
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
import java.util.function.Predicate;

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

    public Page<Plaza> consultarPlazas(Integer planta, TipoPlaza tipoPlaza, EstadoPlaza estadoPlaza) {
        return repository.findAllByPlantaAndTipoAndEstado(planta, tipoPlaza, estadoPlaza, PageRequest.of(0, 10));
    }

    public PlazasOcupacionDTO consultarPlazasOcupacion() {
        List<Plaza> plazas = repository.findAll();
        return new PlazasOcupacionDTO(
                calcularEstadistica(plazas),
                calcularPorTipo(plazas),
                new PlazasOcupacionDTO.Planta(
                        calcularPlanta(plazas, 0),
                        calcularPlanta(plazas, -1),
                        calcularPlanta(plazas, -2)
                )
        );
    }

    private PlazasOcupacionDTO.Resumen calcularPlanta(List<Plaza> plazas, int numeroPlanta) {
        List<Plaza> plazasPlanta = plazas.stream()
                .filter(plaza -> plaza.getPlanta().equals(numeroPlanta))
                .toList();
        PlazasOcupacionDTO.Estadistica estadistica = calcularEstadistica(plazasPlanta);
        return new PlazasOcupacionDTO.Resumen(
                estadistica.libres(), estadistica.ocupadas(), estadistica.bloqueadas(), estadistica.capacidad(),
                calcularPorTipo(plazasPlanta)
        );
    }

    private PlazasOcupacionDTO.Tipo calcularPorTipo(List<Plaza> plazas) {
        return new PlazasOcupacionDTO.Tipo(
                calcularEstadistica(plazas, plaza -> plaza.getTipo() == TipoPlaza.NORMAL),
                calcularEstadistica(plazas, plaza -> plaza.getTipo() == TipoPlaza.PMR),
                calcularEstadistica(plazas, plaza -> plaza.getTipo() == TipoPlaza.ELECTRICA)
        );
    }

    private PlazasOcupacionDTO.Estadistica calcularEstadistica(List<Plaza> plazas) {
        return calcularEstadistica(plazas, plaza -> true);
    }

    private PlazasOcupacionDTO.Estadistica calcularEstadistica(List<Plaza> plazas, Predicate<Plaza> filtro) {
        List<Plaza> filtradas = plazas.stream().filter(filtro).toList();
        long ocupadas = filtradas.stream().filter(plaza -> plaza.getEstado() == EstadoPlaza.OCUPADA).count();
        long bloqueadas = filtradas.stream().filter(plaza -> plaza.getEstado() == EstadoPlaza.BLOQUEADA).count();
        long capacidad = filtradas.size();
        return new PlazasOcupacionDTO.Estadistica(capacidad - ocupadas - bloqueadas, ocupadas, bloqueadas, capacidad);
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
        } else if (plaza.getEstado().equals(estadoLibre)) {
            plaza.setEstado(estadoBloqueado);
        }
        repository.save(plaza);
    }

    public List<Plaza> consultarPlazasPorPlanta(Integer planta) {
        return repository.findByPlantaOrderByNumeroAsc(planta);
    }

}
