package es.safareyes.parkcontrol.dtos.request;

import jakarta.validation.constraints.NotNull;

public record AplicarDescuentoRequest(

    @NotNull(message = "El ID de la promoción es obligatorio")
    Integer idPromocion

) {}
