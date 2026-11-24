package es.safareyes.parkcontrol.dtos.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActualizarTarifaDTO(

        String nombre,
        Integer topeMaximo,
        LocalDateTime fecha,
        Integer minCortesia,
        BigDecimal precio

) {}
