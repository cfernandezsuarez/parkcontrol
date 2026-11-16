package es.safareyes.parkcontrol.dtos.response;

import java.math.BigDecimal;

public record ImporteTicketResponse(

        Integer ticketId,
        String matricula,
        Long minutosTranscurridos,
        Long minutosCortesia,
        Long minutosCobrados,
        BigDecimal importe,
        String mensaje

) {}
