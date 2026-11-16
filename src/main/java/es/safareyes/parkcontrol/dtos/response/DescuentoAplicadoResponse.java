package es.safareyes.parkcontrol.dtos.response;

import java.math.BigDecimal;

public record DescuentoAplicadoResponse(

    Integer ticketId,

    String nombrePromocion,

    Integer minutosDescontados,

    Integer minutosTotalesDescuento,

    BigDecimal importeActual,

    String mensaje

) {}
