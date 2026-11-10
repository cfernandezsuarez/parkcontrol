package es.safareyes.parkcontrol.dtos;

public record PlazasOcupacionDTO(
        Estadistica total,
        Tipo tipo,
        Planta planta
) {

    public record Estadistica(Long libres, Long ocupadas, Long bloqueadas, Long capacidad) {
    }

    public record Tipo(Estadistica normal, Estadistica pmr, Estadistica electrica) {
    }

    public record Planta(Resumen primera, Resumen segunda, Resumen tercera) {
    }

    public record Resumen(Long libres, Long ocupadas, Long bloqueadas, Long capacidad, Tipo tipo) {
    }
}
