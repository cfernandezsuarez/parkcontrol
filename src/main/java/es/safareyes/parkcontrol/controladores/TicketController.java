package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.AplicarDescuentoRequest;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import es.safareyes.parkcontrol.dtos.response.ImporteTicketResponse;
import es.safareyes.parkcontrol.dtos.response.TicketAbonadoResponse;
import es.safareyes.parkcontrol.servicios.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    private TicketService service;

    // POST 7
    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@Valid @RequestBody TicketEntradaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(request));
    }

    // GET 8
    @GetMapping("/{matricula}/importe")
    public ResponseEntity<ImporteTicketResponse> importeTicket(@PathVariable(name = "matricula") String matricula) {
        return ResponseEntity.ok(service.importeTicket(matricula));
    }

    // POST 9
    @PostMapping("/{id}/descuentos")
    public ResponseEntity<String> aplicarPromocion(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody AplicarDescuentoRequest request
            ) {
        return ResponseEntity.ok(service.aplicarPromocion(id, request));
    }

    @GetMapping("/mis-tickets")
    public ResponseEntity<List<TicketAbonadoResponse>> obtenerMisTickets(Authentication authentication) {
        return ResponseEntity.ok(service.obtenerMisTickets(authentication.getName()));
    }

}
