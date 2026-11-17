package es.safareyes.parkcontrol.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketPagadoResponse(

        Integer ticketId,

        String matricula,

        LocalDateTime horaEntrada,

        LocalDateTime horaSalida,

        Integer minutosTotales,

        Integer minutosDescuento,

        BigDecimal importeFinal,

        Integer numeroPlaza

) {}
