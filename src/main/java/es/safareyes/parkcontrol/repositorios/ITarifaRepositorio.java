package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITarifaRepositorio extends JpaRepository<Tarifa, Integer> {

    @Query(value = "select ti.tarifa from Ticket ti where ti.estado = EstadoTicket.ABIERTO")
    List<Tarifa> tarifaVigor();

}
