package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlazaRepository extends JpaRepository<Plaza, Integer> {

    List<Plaza> findByPlantaAndTipoAndEstado(Integer planta, TipoPlaza tipo, Boolean estado);

    @Query(value = "select p from Plaza p where p.estado = true")
    List<Plaza> plazasLibresPorPlantaTipo(Integer planta, TipoPlaza tipoPlaza);
}
