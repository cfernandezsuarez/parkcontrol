package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.AbonadoMapper;
import es.safareyes.parkcontrol.dtos.request.CrearAbonadoRequest;
import es.safareyes.parkcontrol.dtos.response.AbonadoResponse;
import es.safareyes.parkcontrol.exceptions.MatriculaInvalida;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.Abonado;
import es.safareyes.parkcontrol.repositorios.IAbonadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AbonadoService {

    private IAbonadoRepository repository;
    private AbonadoMapper mapper;

    public String altaDeAbonado(CrearAbonadoRequest request) {

        // RN-01
        if (repository.existsByMatricula(request.matricula().toUpperCase())) {
            throw new MatriculaInvalida("Ya existe un abonado con esta matrícula.");
        }

        // RN-02
        if (request.fechaFin().isBefore(request.fechaInicio())) {
            throw new ReglaNegocio("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Abonado abonado = new Abonado();
        abonado.setEmail(request.email());
        abonado.setNombre(request.nombre());
        abonado.setMatricula(request.matricula().toUpperCase());
        abonado.setFechaInicio(request.fechaInicio());
        abonado.setFechaFin(request.fechaFin());

        repository.save(abonado);

        return "Abonado creado correctamente";
    }

    @Transactional(readOnly = true)
    public List<AbonadoResponse> obtenerAbonados() {
        return repository.findAll().stream()
                .map(mapper::toAbonadoDto)
                .toList();
    }

}
