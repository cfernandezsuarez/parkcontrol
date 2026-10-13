package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.dtos.OcupacionPlanta;
import es.safareyes.parkcontrol.dtos.OcupacionPorTipo;
import es.safareyes.parkcontrol.dtos.TotalPlazasOcupadas;
import es.safareyes.parkcontrol.modelos.EstadoPlaza;
import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Repository
public interface IPlazaRepository extends JpaRepository<Plaza, Integer> {

    Page<Plaza> findAllByPlantaAndTipoAndEstado(Integer planta, TipoPlaza tipo, EstadoPlaza estado, Pageable pageable);

    @Query(value = "select p from Plaza p where p.estado = es.safareyes.parkcontrol.modelos.EstadoPlaza.LIBRE and p.planta = :planta and p.tipo = :tipoPlaza")
    List<Plaza> plazasLibresPorPlantaTipo(
            @Param("planta") Integer planta,
            @Param("tipoPlaza") TipoPlaza tipoPlaza);

    @Query(value = "select p.planta, count(DISTINCT p.id) from Plaza p join Ticket t on t.plaza = p where t.horaSalida is null group by p.planta order by p.planta")
    List<OcupacionPlanta> ocupacionPlanta(@Param("estado") EstadoTicket estado);

    @Query(value = "select " +
            "new es.safareyes.parkcontrol.dtos.OcupacionPorTipo(" +
            "sum(case when p.tipo = es.safareyes.parkcontrol.modelos.TipoPlaza.NORMAL then 1 else 0 end), " +
            "sum(case when p.tipo = es.safareyes.parkcontrol.modelos.TipoPlaza.PMR then 1 else 0 end)," +
            "sum(case when p.tipo = es.safareyes.parkcontrol.modelos.TipoPlaza.ELECTRICA then 1 else 0 end)) " +
            "from Plaza p where p.estado = es.safareyes.parkcontrol.modelos.EstadoPlaza.OCUPADA")
    List<OcupacionPorTipo> opcupacionPorTipos();

    @Query(value = "select count(p) from Plaza p where p.estado = es.safareyes.parkcontrol.modelos.EstadoPlaza.OCUPADA")
    List<TotalPlazasOcupadas> totalPlazasOcupadas();
}
