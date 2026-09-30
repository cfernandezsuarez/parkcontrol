package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPromocionRepository extends JpaRepository<Promocion, Integer> {
}
