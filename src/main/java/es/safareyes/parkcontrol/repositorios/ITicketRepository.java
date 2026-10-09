package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.modelos.Abonado;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t.id, t.importe from Ticket t where t.matricula = :matricula and t.estado = EstadoTicket.ABIERTO")
    List<ImporteTicket> importeTicketAbierto(@Param("matricula") String matricula);

    @Query(value = "select t.* from abonado a join ticket_abonado ti on ti.id_abonado = a.id join ticket t on ti.id_ticket = t.id where a.id = :id", nativeQuery = true)
    List<Ticket> listadoTicketPorAbonado(@Param("id") Integer id);
}
