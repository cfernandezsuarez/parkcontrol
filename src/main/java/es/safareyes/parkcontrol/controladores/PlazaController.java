package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.CrearNuevaPlazaDTO;
import es.safareyes.parkcontrol.dtos.OcupacionPorPlanta;
import es.safareyes.parkcontrol.dtos.TotalPlazasOcupadas;
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

    @GetMapping
    public Page<Plaza> consultarPlazas(){
        return service.consultarPlazas(1, TipoPlaza.NORMAL, EstadoPlaza.OCUPADA);
    }

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

    @PatchMapping("/{id}/estado")
    public String bloquearPlaza(@PathVariable(name = "id") Integer id) {
        service.bloquearPlaza(id);
        Plaza plaza = service.consultarPlazaPorId(id);
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

    @GetMapping("/ocupadas/total")
    public List<TotalPlazasOcupadas> consultarPlazasTotalesOcupadas() {
        return service.consultarPlazasTotalesOcupadas();
    }

    @GetMapping("/ocupadas/planta")
    public OcupacionPorPlanta ocupacionPorPlanta() {
        return service.consultarOcupacionPorPlanta();
    }

    @GetMapping("/disponibilidad")
    public List<Plaza> consultarPlazasDisponibles(){
        return service.consultarPlazasDisponibles(1, TipoPlaza.NORMAL);
    };

}
