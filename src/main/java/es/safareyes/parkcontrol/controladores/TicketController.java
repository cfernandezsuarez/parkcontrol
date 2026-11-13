package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
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

    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@Valid @RequestBody TicketEntradaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(request));
    }

    @GetMapping("/mis-tickets")
    public ResponseEntity<List<TicketAbonadoResponse>> obtenerMisTickets(Authentication authentication) {
        return ResponseEntity.ok(service.obtenerMisTickets(authentication.getName()));
    }

}
