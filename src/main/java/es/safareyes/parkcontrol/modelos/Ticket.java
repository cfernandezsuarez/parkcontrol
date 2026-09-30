package es.safareyes.parkcontrol.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "parkcontrol", name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "minutos_totales")
    private Integer minutosTotales;

    @Column(name = "estado")
    @Enumerated(EnumType.ORDINAL)
    private EstadoTicket estado;

    @Column(name = "importe")
    private BigDecimal importe;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "importe_descuento")
    private BigDecimal importeDescuento;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "hora_entrada")
    private LocalDateTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarifa")
    private Tarifa tarifa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plaza")
    private Plaza plaza;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "parkcontrol",
            name = "ticket_abonado",
            joinColumns = @JoinColumn(name = "id_ticket"),
            inverseJoinColumns = @JoinColumn(name = "id_abonado")
    )
    private List<Abonado> abonado;
}
