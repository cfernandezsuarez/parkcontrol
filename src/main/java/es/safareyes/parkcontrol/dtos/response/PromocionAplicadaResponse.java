package es.safareyes.parkcontrol.dtos.response;

import java.math.BigDecimal;

public record PromocionAplicadaResponse(

    Integer ticketId,

    String nombrePromocion,

    Integer minutosDescontados,

    Integer minutosTotalesDescuento

) {}
