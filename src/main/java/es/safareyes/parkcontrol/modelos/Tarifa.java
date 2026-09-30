package es.safareyes.parkcontrol.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "parkcontrol", name = "tarifa")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tope_maximo")
    private Integer topeMaximo;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "min_cortesia")
    private Integer minCortesia;

    @Column(name = "precio")
    private BigDecimal precio;
}
