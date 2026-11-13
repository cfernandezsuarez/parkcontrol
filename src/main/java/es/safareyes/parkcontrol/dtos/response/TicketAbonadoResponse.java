package es.safareyes.parkcontrol.dtos.response;

import es.safareyes.parkcontrol.modelos.EstadoTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketAbonadoResponse(
        Integer id,
        String matricula,
        LocalDateTime horaEntrada,
        LocalDateTime horaSalida,
        Integer minutosTotales,
        BigDecimal importe,
        EstadoTicket estado,
        Integer numeroPlaza
) {}
