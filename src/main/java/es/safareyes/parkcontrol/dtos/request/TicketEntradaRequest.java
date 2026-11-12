package es.safareyes.parkcontrol.dtos.request;

import es.safareyes.parkcontrol.modelos.TipoPlaza;
import jakarta.validation.constraints.NotNull;

public record TicketEntradaRequest(

    @NotNull(message = "La matrícula es obligatoria")
    String matricula,

    TipoPlaza tipoPlaza
) {}
