package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IPlazaRepository extends JpaRepository<Plaza, Integer> {

    List<Plaza> findAllByPlantaAndTipoAndEstado(Integer planta, TipoPlaza tipo, Boolean estado);

    @Query(value = "select p from Plaza p where p.estado = true and p.planta = :planta and p.tipo = :tipoPlaza")
    List<Plaza> plazasLibresPorPlantaTipo(
            @Param("planta") Integer planta,
            @Param("tipoPlaza") TipoPlaza tipoPlaza);
}
