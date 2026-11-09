package es.safareyes.parkcontrol.dtos;

import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import jakarta.validation.constraints.NotNull;

public record CrearNuevaPlazaDTO (

    @NotNull(message = "El numero de la plaza es obligatoria")
    Integer numero,

    Integer planta,

    TipoPlaza tipo,

    EstadoPlaza estado
){}
