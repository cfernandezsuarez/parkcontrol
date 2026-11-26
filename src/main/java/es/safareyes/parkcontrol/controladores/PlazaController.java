package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.PlazasOcupacionDTO;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.servicios.PlazaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plazas")
@AllArgsConstructor
public class PlazaController {

    private PlazaService service;

    // GET 1
    @GetMapping
    public Page<Plaza> consultarPlazas(
            @RequestParam(name = "planta") Integer planta,
            @RequestParam(name = "tipo") TipoPlaza tipo,
            @RequestParam(name = "estado") EstadoPlaza estado
            ){
        return service.consultarPlazas(planta, tipo, estado);
    }

    // POST 3
    @PostMapping
    public void crearNuevaPlaza(
            @RequestParam(name = "numero") Integer numero,
            @RequestParam(name = "planta") Integer planta,
            @RequestParam(name = "tipo") TipoPlaza tipo,
            @RequestParam(name = "estado") EstadoPlaza estado
            ) {
        CrearNuevaPlazaDTO plazaDTO = new CrearNuevaPlazaDTO(numero, planta, tipo, estado);
        service.crearNuevaPlaza(plazaDTO);
    }

    // PATCH 4
    @PatchMapping("/{id}/estado")
    public String bloquearPlaza(@PathVariable(name = "id") Integer id) {
        service.bloquearPlaza(id);
        Plaza plaza = service.consultarPlazaPorId(id);

        if (plaza.getEstado() == EstadoPlaza.OCUPADA) {
            throw new ReglaNegocio("No se puede bloquear una plaza ocupada");
        }

        return plaza.getEstado() == EstadoPlaza.BLOQUEADA ? "La plaza ha sido bloqueada correctamente" : "La plaza ha sido desbloqueada correctamente";
    }

    @GetMapping("/total")
    public Long consultarPlazasTotales() {
        return service.consultarPlazasTotales();
    }

    @GetMapping("/total/bloqueada")
    public Long consultarPlazasBloqueadas() {
        return service.consultarPlazasEnMantenimiento();
    }

    @GetMapping("/total/planta")
    public Long consultarPlazasPorPlantaMaxima(@RequestParam Integer planta) {
        return service.consultarPlazasPorPlantaMaxima(planta);
    }

    @GetMapping("/ocupacion")
    public PlazasOcupacionDTO consultarPlazasOcupacion() {
        return service.consultarPlazasOcupacion();
    }

    // GET 2
    @GetMapping("/disponibilidad")
    public List<Plaza> consultarPlazasDisponibles(
            @RequestParam Integer planta,
            @RequestParam TipoPlaza tipo
            ){
        return service.consultarPlazasDisponibles(planta, tipo);
    };

    @GetMapping("/planta/{planta}")
    public List<Plaza> consultarPlazasPorPlanta(@PathVariable Integer planta) {
        return service.consultarPlazasPorPlanta(planta);
    }

}
