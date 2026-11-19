package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.response.OcupacionPlantaResponse;
import es.safareyes.parkcontrol.dtos.response.RecaudacionDiaResponse;
import es.safareyes.parkcontrol.servicios.InformeService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/informes")
@AllArgsConstructor
public class InformeController {

    private InformeService service;

    @GetMapping("/recaudacion")
    public ResponseEntity<List<RecaudacionDiaResponse>> obtenerRecaudacionDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate hasta
            ) {
        return ResponseEntity.ok(service.obtenerRecaudacionDia(desde, hasta));
    }

    @GetMapping("/ocupacion")
    public ResponseEntity<List<OcupacionPlantaResponse>> obtenerOcupacion() {
        return ResponseEntity.ok(service.obtenerOcupacionPorPlanta());
    }

}
