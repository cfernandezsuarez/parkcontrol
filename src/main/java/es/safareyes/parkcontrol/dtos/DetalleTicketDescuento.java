package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DetalleTicketDescuento {
    private Integer ticket;
    private String matricula;
    private BigDecimal importe;
    private BigDecimal descuento;
}
