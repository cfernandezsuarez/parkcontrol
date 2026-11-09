package es.safareyes.parkcontrol.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CrearNuevaTarifaDTO (

        Integer topeMaximo,

        LocalDateTime fecha,

        Integer minCortesia,

        BigDecimal precio
) {}
