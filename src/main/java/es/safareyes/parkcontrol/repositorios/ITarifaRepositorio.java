package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITarifaRepositorio extends JpaRepository<Tarifa, Integer> {
}
