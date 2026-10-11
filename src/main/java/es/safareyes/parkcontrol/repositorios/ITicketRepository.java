package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.modelos.Abonado;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t.id, t.importe from Ticket t where t.matricula = :matricula and t.estado = EstadoTicket.ABIERTO")
    List<ImporteTicket> importeTicketAbierto(@Param("matricula") String matricula);

    @Query("select t from Ticket t where :abonado member of t.abonado and (:estado is null or t.estado = :estado) and (:precio1 is null or t.importe >= :precio1) and (:precio2 is null or t.importe <= :precio2) and (:minutos1 is null or t.minutosTotales >= :minutos1) and (:minutos2 is null or t.minutosTotales >= :minutos2)")
    List<Ticket> ticketsPorAbonado(
            @Param("abonado") Abonado abonado,
            @Param("estado") EstadoTicket estadoTicket,
            @Param("precio1") BigDecimal precio1,
            @Param("precio2") BigDecimal precio2,
            @Param("minutos1") Integer minutos1,
            @Param("minutos2") Integer minutos2
            );

    List<Ticket> findAllById(Integer id);
}
