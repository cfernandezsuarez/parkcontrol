package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.dtos.request.PagarTicketRequest;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.modelos.Ticket;
import es.safareyes.parkcontrol.repositorios.IAbonadoRepository;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import es.safareyes.parkcontrol.servicios.TicketService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    private final ITicketRepository tickets = mock(ITicketRepository.class);
    private final IAbonadoRepository abonados = mock(IAbonadoRepository.class);
    private final TicketService service = new TicketService(tickets, null, null, null, null, abonados, null);

    @Test
    void consultaYcierreAplicanElAbonoDelPrimerTicketYCobranElSegundo() {
        Ticket primero = ticketAbierto(1, "1234BCD");
        Ticket segundo = ticketAbierto(2, "5678FGH");
        when(abonados.existsByMatriculaAndVigente(eq(primero.getMatricula()), any(LocalDateTime.class)))
                .thenReturn(true);
        when(abonados.existsByMatriculaAndVigente(eq(segundo.getMatricula()), any(LocalDateTime.class)))
                .thenReturn(false);
        when(tickets.minutosDescuentoAcumulados(segundo.getId())).thenReturn(2);

        var consultaPrimero = service.importeTicket(primero.getMatricula());
        assertEquals(0, consultaPrimero.importe().signum());
        assertEquals(0L, consultaPrimero.minutosCobrados());
        assertTrue(consultaPrimero.mensaje().contains("Abono vigente"));
        assertEquals(EstadoTicket.ABIERTO, primero.getEstado());

        var cierrePrimero = service.pagarTicket(new PagarTicketRequest(primero.getMatricula(), null));
        assertEquals(0, cierrePrimero.importeFinal().signum());
        assertEquals(0, primero.getImporte().signum());
        assertEquals(EstadoTicket.CERRADO, primero.getEstado());
        verify(tickets).save(primero);

        var consultaSegundo = service.importeTicket(segundo.getMatricula());
        assertEquals(8L, consultaSegundo.minutosCobrados());
        assertEquals(0, BigDecimal.valueOf(8).compareTo(consultaSegundo.importe()));
        var cierreSegundo = service.pagarTicket(new PagarTicketRequest(segundo.getMatricula(), null));
        assertEquals(consultaSegundo.importe(), cierreSegundo.importeFinal());
        assertEquals(cierreSegundo.importeFinal(), segundo.getImporte());
        assertEquals(EstadoTicket.CERRADO, segundo.getEstado());
        verify(tickets).save(segundo);
    }

    private Ticket ticketAbierto(int id, String matricula) {
        Tarifa tarifa = new Tarifa();
        tarifa.setMinCortesia(1);
        tarifa.setPrecio(BigDecimal.ONE);

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setMatricula(matricula);
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setHoraEntrada(LocalDateTime.now().minusMinutes(10).minusSeconds(30));
        ticket.setTarifa(tarifa);
        when(tickets.findByMatriculaAndEstado(matricula, EstadoTicket.ABIERTO))
                .thenReturn(Optional.of(ticket));
        return ticket;
    }
}
