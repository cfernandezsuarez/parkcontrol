package es.safareyes.parkcontrol.dtos.response;

import java.time.LocalDateTime;

public record AbonadoResponse(

        Integer id,
        String email,
        String nombre,
        String matricula,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin

) {}