package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.servicios.TarifaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarifas")
@AllArgsConstructor
public class TarifaController {

    private TarifaService service;

    @GetMapping("/vigente")
    public List<Tarifa> tarifasVigor() {
        return service.tarifasVigor();
    }
}
