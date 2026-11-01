package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OcupacionPorPlanta {
    private Long primera;
    private Long segunda;
    private Long tercera;
}
