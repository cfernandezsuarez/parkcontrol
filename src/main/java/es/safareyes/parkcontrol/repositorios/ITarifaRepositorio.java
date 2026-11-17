package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ITarifaRepositorio extends JpaRepository<Tarifa, Integer> {

    // GET 5
    @Query(value = "SELECT t FROM Tarifa t WHERE t.fecha <= :fecha ORDER BY t.fecha DESC LIMIT 1")
    Optional<Tarifa> tarifaVigente(@Param("fecha") LocalDateTime fecha);

}
