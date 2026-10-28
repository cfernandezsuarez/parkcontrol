package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.servicios.PlazaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plazas")
@AllArgsConstructor
public class PlazaController {

    private PlazaService service;

    @GetMapping
    public Page<Plaza> consultarPlazas(){
        return service.consultarPlazas(1, TipoPlaza.NORMAL, EstadoPlaza.OCUPADA);
    };

    @GetMapping("/disponibilidad")
    public List<Plaza> consultarPlazasDisponibles(){
        return service.consultarPlazasDisponibles(1, TipoPlaza.NORMAL);
    };

}
