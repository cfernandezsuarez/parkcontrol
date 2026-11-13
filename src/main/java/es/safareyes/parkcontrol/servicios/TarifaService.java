package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TarifaService {

    private ITarifaRepositorio repository;

    public Optional<Tarifa[]> tarifasVigor() {
        return repository.tarifaVigor();
    }

    public void crearNuevaTarifa(CrearNuevaTarifaDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(dto.nombre());
        tarifa.setTopeMaximo(dto.topeMaximo());
        tarifa.setFecha(dto.fecha());
        tarifa.setMinCortesia(dto.minCortesia());
        tarifa.setPrecio(dto.precio());
        repository.save(tarifa);
    }
}
