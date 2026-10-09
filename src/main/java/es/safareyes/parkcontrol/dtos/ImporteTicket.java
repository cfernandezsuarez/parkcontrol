package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ImporteTicket {
    private Integer id;
    private BigDecimal importe;
}
