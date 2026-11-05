package es.safareyes.parkcontrol.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest (
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50)
    String nombre,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password
) {}
