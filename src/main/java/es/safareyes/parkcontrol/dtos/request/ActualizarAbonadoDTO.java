package es.safareyes.parkcontrol.dtos.request;

import java.time.LocalDateTime;

public record ActualizarAbonadoDTO(

        String email,
        String nombre,
        String matricula,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin

) {}
