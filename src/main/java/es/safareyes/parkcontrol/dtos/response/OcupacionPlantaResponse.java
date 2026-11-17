package es.safareyes.parkcontrol.dtos.response;

public record OcupacionPlantaResponse(

        Integer planta,
        Long ocupadas,
        Long estanciaMediaMinutos

) {}
