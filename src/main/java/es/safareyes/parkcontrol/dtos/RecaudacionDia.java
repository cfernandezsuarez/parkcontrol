package es.safareyes.parkcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecaudacionDia {
    private Long dia;
    private BigDecimal importeTotal;
}
