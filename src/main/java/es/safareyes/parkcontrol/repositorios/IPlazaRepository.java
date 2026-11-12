package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface IPlazaRepository extends JpaRepository<Plaza, Integer> {

    Page<Plaza> findAllByPlantaAndTipoAndEstado(Integer planta, TipoPlaza tipo, EstadoPlaza estado, Pageable pageable);

    @Query(value = "select p from Plaza p where p.estado = es.safareyes.parkcontrol.modelos.EstadoPlaza.LIBRE and p.planta = :planta and p.tipo = :tipoPlaza")
    List<Plaza> plazasLibresPorPlantaTipo(
            @Param("planta") Integer planta,
            @Param("tipoPlaza") TipoPlaza tipoPlaza);

    @Query(value = "SELECT p FROM Plaza p WHERE p.estado = :estado AND p.tipo = :tipo ORDER BY p.planta DESC, p.numero ASC")
    List<Plaza> plazasLibresPorTipo(
            @Param("estado") EstadoPlaza estado,
            @Param("tipo") TipoPlaza tipo,
            Pageable pageable
    );

    List<Plaza> countPlazaByEstado(EstadoPlaza estado);

    long countByEstado(EstadoPlaza estado);

    long countByPlanta(Integer planta);

    List<Plaza> findByPlantaOrderByNumeroAsc(Integer planta);
}
