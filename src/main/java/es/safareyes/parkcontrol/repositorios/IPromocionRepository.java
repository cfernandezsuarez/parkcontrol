package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromocionRepository extends JpaRepository<Promocion, Integer> {

    @Query("select p from Promocion p")
    List<Promocion> catalogoPromociones();
}
