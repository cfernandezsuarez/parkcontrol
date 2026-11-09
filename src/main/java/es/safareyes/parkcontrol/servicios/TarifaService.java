package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.modelos.Plaza;
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

    public void crearNuevaPlaza(CrearNuevaTarifaDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setTopeMaximo(dto.topeMaximo());
        tarifa.setFecha(dto.fecha());
        tarifa.setMinCortesia(dto.minCortesia());
        tarifa.setPrecio(dto.precio());
        repository.save(tarifa);
    }
}
