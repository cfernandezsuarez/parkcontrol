package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.AplicarDescuentoRequest;
import es.safareyes.parkcontrol.dtos.request.PagarTicketRequest;
import es.safareyes.parkcontrol.dtos.request.TicketEntradaRequest;
import es.safareyes.parkcontrol.dtos.response.*;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.servicios.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<PromocionAplicadaResponse> aplicarPromocion(
            @PathVariable(name = "id") Integer id,
            @Valid @RequestBody AplicarDescuentoRequest request
            ) {
        return ResponseEntity.ok(service.aplicarPromocion(id, request));
    }

    // POST 10
    @PostMapping("/pagar")
    public ResponseEntity<TicketPagadoResponse> pagarTicket(@Valid @RequestBody PagarTicketRequest request) {
        return ResponseEntity.ok(service.pagarTicket(request));
    }

    // GET 11
    @GetMapping
    public ResponseEntity<Page<TicketAbonadoResponse>> obtenerMisTickets(
            @RequestParam(required = false) EstadoTicket estado,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) Integer minutosMin,
            @RequestParam(required = false) Integer minutosMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {

        Boolean esAbonado = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ABONADO"));

        return ResponseEntity.ok(service.obtenerMisTickets(authentication.getName(), esAbonado, estado, precioMin, precioMax, minutosMin, minutosMax, page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketDetalleResponse> obtenerDetalleTicket(
            @PathVariable(name = "id") Integer id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(service.obtenerDetalleTicket(id, authentication));
    }

}
