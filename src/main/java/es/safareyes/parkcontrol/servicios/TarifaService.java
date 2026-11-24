package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.TarifaMapper;
import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.dtos.request.ActualizarTarifaDTO;
import es.safareyes.parkcontrol.exceptions.ElementoNoEncontrado;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TarifaService {

    private ITarifaRepositorio repository;
    private ITicketRepository ticketRepository;

    private TarifaMapper mapper;

    public Tarifa tarifasVigor() {
        return repository.tarifaVigente(LocalDateTime.now()).orElseThrow(() -> new ReglaNegocio("No hay tarifa vigente"));
    }

    public void crearNuevaTarifa(CrearNuevaTarifaDTO dto) {

        Tarifa tarifa = mapper.toEntity(dto);

        repository.save(tarifa);
    }

    public List<Tarifa> obtenerTarifas() {
        return repository.findAll();
    }

    public String eliminarTarifa(Integer id) {

        Tarifa tarifa = repository.findById(id).
                orElseThrow(() -> new ElementoNoEncontrado("No se ha encontrado la tarifa"));

        if (ticketRepository.existsByTarifaId(id)) {
            throw new ReglaNegocio("No se puede eliminar la tarifa porque tiene tickets asociados");
        }

        repository.deleteById(id);

        return "Tarifa eliminada correctamente";
    }

    public Tarifa actualizarTarifa(Integer id, ActualizarTarifaDTO dto) {
        Tarifa tarifa = repository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontrado("Tarifa no encontrada"));

        if (dto.nombre() != null) tarifa.setNombre(dto.nombre());
        if (dto.topeMaximo() != null) tarifa.setTopeMaximo(dto.topeMaximo());
        if (dto.fecha() != null) tarifa.setFecha(dto.fecha());
        if (dto.minCortesia() != null) tarifa.setMinCortesia(dto.minCortesia());
        if (dto.precio() != null) tarifa.setPrecio(dto.precio());

        return repository.save(tarifa);
    }
}
