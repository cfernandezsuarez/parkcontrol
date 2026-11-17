package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.dtos.DetalleTicketDescuento;
import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.dtos.response.RecaudacionDiaResponse;
import es.safareyes.parkcontrol.modelos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {

    // GET 8
    @Query("select t.id, t.importe from Ticket t where t.matricula = :matricula and t.estado = EstadoTicket.ABIERTO")
    List<ImporteTicket> importeTicketAbierto(@Param("matricula") String matricula);

    // GET 11
    @Query("select t from Ticket t where :abonado member of t.abonado and (:estado is null or t.estado = :estado) and (:precio1 is null or t.importe >= :precio1) and (:precio2 is null or t.importe <= :precio2) and (:minutos1 is null or t.minutosTotales >= :minutos1) and (:minutos2 is null or t.minutosTotales <= :minutos2)")
    Page<Ticket> ticketsPorAbonado(
            @Param("abonado") Abonado abonado,
            @Param("estado") EstadoTicket estadoTicket,
            @Param("precio1") BigDecimal precio1,
            @Param("precio2") BigDecimal precio2,
            @Param("minutos1") Integer minutos1,
            @Param("minutos2") Integer minutos2,
            Pageable pageable
            );

    @Query("SELECT t FROM Ticket t WHERE (:estado IS NULL OR t.estado = :estado) AND (:precioMin IS NULL OR t.importe >= :precioMin) AND (:precioMax IS NULL OR t.importe <= :precioMax) AND (:minutosMin IS NULL OR t.minutosTotales >= :minutosMin) AND (:minutosMax IS NULL OR t.minutosTotales <= :minutosMax)")
    Page<Ticket> todosLosTickets(
            @Param("estado") EstadoTicket estado,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("minutosMin") Integer minutosMin,
            @Param("minutosMax") Integer minutosMax,
            Pageable pageable
            );

    // GET 12
    @Query("select t.id, t.matricula, t.importe, t.importe - coalesce(t.importeDescuento, 0) from Abonado a join a.ticket t where a.id = :idAbonado order by t.id")
    List<DetalleTicketDescuento> ticketsDescuentos(@Param("idAbonado")Integer id);

    // GET 15
    @Query(value = "select row_number() over (order by date_trunc('day', t.horaSalida)) as dia, sum(t.importe) as importe_total from Ticket t where t.horaSalida >= :horaInicio and t.horaSalida <= :horaFin group by date_trunc('day', t.horaSalida) ")
    List<RecaudacionDiaResponse> recaudacionPorDia(
            @Param("horaInicio") LocalDateTime horaInicio,
            @Param("horaFin") LocalDateTime horaFin);

    // GET 16
    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (t.hora_salida - t.hora_entrada)) / 60) FROM parkcontrol.ticket t WHERE t.hora_salida IS NOT NULL", nativeQuery = true)
    Double estanciaMediaMinutos();

    boolean existsByMatriculaAndEstado(String matricula, EstadoTicket estado);

    @Query(value = "SELECT t.* FROM parkcontrol.ticket t JOIN parkcontrol.ticket_abonado ta ON t.id = ta.id_ticket JOIN parkcontrol.abonado a ON ta.id_abonado = a.id JOIN parkcontrol.usuario u ON u.id_abonado = a.id WHERE u.id = :id", nativeQuery = true)
    Page<Ticket> ticketsPorIdUsuario(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT t FROM Ticket t WHERE t.matricula = :matricula AND t.estado = EstadoTicket.ABIERTO")
    Optional<Ticket> findByMatriculaAndEstado(@Param("matricula") String matricula, @Param("estado") EstadoTicket estado);

    @Query("SELECT COALESCE(SUM(p.minDescuento), 0) FROM Promocion p WHERE p.ticket.id = :idTicket")
    Integer minutosDescuentoAcumulados(@Param("idTicket") Integer idTicket);
}
