package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OcupacionPorTipo {
    private Long normal;
    private Long pmr;
    private Long electrico;
}
