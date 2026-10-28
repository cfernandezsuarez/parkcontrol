package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.servicios.PlazaService;
import lombok.AllArgsConstructor;
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
    public List<Plaza> consultarPlazas(){
        return service.consultarPlazas();
    };

    @GetMapping("/disponibilidad")
    public List<Plaza> consultarPlazasDisponibles(){
        return service.consultarPlazasDisponibles();
    };

}
