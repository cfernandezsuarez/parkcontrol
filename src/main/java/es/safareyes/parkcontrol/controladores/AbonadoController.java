package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.CrearAbonadoRequest;
import es.safareyes.parkcontrol.modelos.Abonado;
import es.safareyes.parkcontrol.servicios.AbonadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Abonado>> obtenerAbonados() {
        return ResponseEntity.ok(service.obtenerAbonados());
    }

}
