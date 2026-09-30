package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, Integer> {
}
