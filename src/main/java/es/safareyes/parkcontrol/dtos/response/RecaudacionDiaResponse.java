package es.safareyes.parkcontrol.dtos.response;

import java.math.BigDecimal;

public record RecaudacionDiaResponse(

        Long dia,
        BigDecimal importeTotal

) {}
