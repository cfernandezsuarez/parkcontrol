package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITarifaRepositorio extends JpaRepository<Tarifa, Integer> {

    @Query(value = "select ti.tarifa from Ticket ti where ti.estado = EstadoTicket.ABIERTO")
    List<Tarifa> tarifaVigor();

    @Query(value = "SELECT t FROM Tarifa t WHERE t.fecha <= :fecha ORDER BY t.fecha DESC")
    Optional<Tarifa> findTarifaVigente(@Param("fecha") LocalDateTime fecha);
}
