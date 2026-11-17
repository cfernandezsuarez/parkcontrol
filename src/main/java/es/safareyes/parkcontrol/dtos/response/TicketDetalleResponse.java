package es.safareyes.parkcontrol.dtos.response;

import es.safareyes.parkcontrol.modelos.EstadoTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TicketDetalleResponse(

        Integer id,
        String matricula,
        LocalDateTime horaEntrada,
        LocalDateTime horaSalida,
        Integer minutosTotales,
        BigDecimal importe,
        EstadoTicket estado,
        Integer numeroPlaza,
        BigDecimal importeDescuento,
        List<DescuentoDetalleDTO> descuentos

) {}
