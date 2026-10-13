package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.dtos.DetalleTicketDescuento;
import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.dtos.RecaudacionDia;
import es.safareyes.parkcontrol.modelos.Abonado;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t.id, t.importe from Ticket t where t.matricula = :matricula and t.estado = EstadoTicket.ABIERTO")
    List<ImporteTicket> importeTicketAbierto(@Param("matricula") String matricula);

    @Query("select t from Ticket t where :abonado member of t.abonado and (:estado is null or t.estado = :estado) and (:precio1 is null or t.importe >= :precio1) and (:precio2 is null or t.importe <= :precio2) and (:minutos1 is null or t.minutosTotales >= :minutos1) and (:minutos2 is null or t.minutosTotales >= :minutos2)")
    Page<Ticket> ticketsPorAbonado(
            @Param("abonado") Abonado abonado,
            @Param("estado") EstadoTicket estadoTicket,
            @Param("precio1") BigDecimal precio1,
            @Param("precio2") BigDecimal precio2,
            @Param("minutos1") Integer minutos1,
            @Param("minutos2") Integer minutos2,
            Pageable pageable
            );

    @Query("select t.id, t.matricula, t.importe, t.importe - coalesce(t.importeDescuento, 0) from Abonado a join a.ticket t where a.id = :idAbonado order by t.id")
    List<DetalleTicketDescuento> ticketsDescuentos(@Param("idAbonado")Integer id);

    @Query("select row_number() over (order by date_trunc('day', t.horaSalida)) as dia, sum(t.importe) as importe_total from Ticket t where t.horaSalida >= :horaInicio and t.horaSalida <= :horaFin group by date_trunc('day', t.horaSalida) ")
    List<RecaudacionDia> recaudacionPorDia(
            @Param("horaInicio") LocalDateTime horaInicio,
            @Param("horaFin") LocalDateTime horaFin);

}
