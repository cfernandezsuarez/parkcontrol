package es.safareyes.parkcontrol.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        @NotNull(message = "El nombre no puede estar vacío")
        @NotEmpty(message = "El nombre no puede estar vacío")
        String nombre,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @NotNull(message = "La contraseña no puede estar vacía")
        @NotEmpty(message = "La contraseña no puede estar vacía")
        String password

) {}
