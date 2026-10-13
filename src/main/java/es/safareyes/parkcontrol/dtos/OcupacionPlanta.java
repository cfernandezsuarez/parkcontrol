package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OcupacionPlanta {
    private Integer planta;
    private Long plazasOcupadas;
}
