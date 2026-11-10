package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.servicios.TarifaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarifas")
@AllArgsConstructor
public class TarifaController {

    private TarifaService service;

    @PostMapping
    public void crearNuevaTarifa(
            @RequestParam(name = "topeMaximo") Integer topeMaximo,
            @RequestParam(name = "fecha")LocalDateTime fecha,
            @RequestParam(name = "minCortesia") Integer minCortesia,
            @RequestParam(name = "precio")BigDecimal precio
            ) {
        CrearNuevaTarifaDTO dto = new CrearNuevaTarifaDTO(topeMaximo, fecha, minCortesia, precio);
        service.crearNuevaTarifa(dto);
    }

    @GetMapping("/vigente")
    public List<Tarifa> tarifasVigor() {
        return service.tarifasVigor();
    }
}
