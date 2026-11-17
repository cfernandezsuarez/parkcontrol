package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.response.PromocionResponse;
import es.safareyes.parkcontrol.servicios.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promociones")
@AllArgsConstructor
public class PromocionController {

    private PromocionService service;

    @GetMapping
    public ResponseEntity<List<PromocionResponse>> obtenerCatalogo() {
        return ResponseEntity.ok(service.obtenerCatalogo());
    }

}
