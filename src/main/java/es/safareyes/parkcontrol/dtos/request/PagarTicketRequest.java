package es.safareyes.parkcontrol.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record PagarTicketRequest(

        @NotBlank(message = "La matrícula es obligatoria")
        String matricula,

        String codigoCine

) {}
