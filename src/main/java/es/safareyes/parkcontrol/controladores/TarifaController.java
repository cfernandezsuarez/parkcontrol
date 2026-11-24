package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.dtos.request.ActualizarTarifaDTO;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.servicios.TarifaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarifas")
@AllArgsConstructor
public class TarifaController {

    private TarifaService service;

    // POST 6
    @PostMapping
    public void crearNuevaTarifa(
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "topeMaximo") Integer topeMaximo,
            @RequestParam(name = "fecha")LocalDateTime fecha,
            @RequestParam(name = "minCortesia") Integer minCortesia,
            @RequestParam(name = "precio")BigDecimal precio
            ) {
        CrearNuevaTarifaDTO dto = new CrearNuevaTarifaDTO(nombre, topeMaximo, fecha, minCortesia, precio);
        service.crearNuevaTarifa(dto);
    }

    // GET 5
    @GetMapping("/vigente")
    public ResponseEntity<Tarifa> tarifasVigor() {
        return ResponseEntity.ok(service.tarifasVigor());
    }

    @GetMapping("/total")
    public ResponseEntity<List<Tarifa>> obtenerTarifas() {
        return ResponseEntity.ok(service.obtenerTarifas());
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<String> eliminarTarifa(
            @PathVariable(name = "id") Integer id
    ) {
        return ResponseEntity.ok(service.eliminarTarifa(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarifa> actualizarTarifa(
            @PathVariable Integer id,
            @RequestBody ActualizarTarifaDTO dto) {
        return ResponseEntity.ok(service.actualizarTarifa(id, dto));
    }
}
