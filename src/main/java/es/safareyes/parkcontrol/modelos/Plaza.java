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
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "planta")
    private Integer planta;

    @Column(name = "tipo")
    @Enumerated(EnumType.ORDINAL)
    private TipoPlaza tipo;

    @Column(name = "estado")
    private Boolean estado;
}
