package es.safareyes.parkcontrol.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "parkcontrol", name = "plaza")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "min_descuento")
    private Integer minDescuento;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private TipoPromocion tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;
}
