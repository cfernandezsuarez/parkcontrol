package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.ActualizarAbonadoDTO;
import es.safareyes.parkcontrol.dtos.request.CrearAbonadoRequest;
import es.safareyes.parkcontrol.dtos.response.AbonadoResponse;
import es.safareyes.parkcontrol.servicios.AbonadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abonados")
@AllArgsConstructor
public class AbonadoController {

    private AbonadoService service;

    @PostMapping
    public ResponseEntity<String> altaDeAbonado(@Valid @RequestBody CrearAbonadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.altaDeAbonado(request));
    }

    @GetMapping
    public ResponseEntity<Page<AbonadoResponse>> obtenerAbonados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.obtenerAbonados(page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AbonadoResponse> actualizarAbonado(
            @PathVariable Integer id,
            @RequestBody ActualizarAbonadoDTO dto) {
        return ResponseEntity.ok(service.actualizarAbonado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAbonado(@PathVariable Integer id) {
        return ResponseEntity.ok(service.eliminarAbonado(id));
    }

}
