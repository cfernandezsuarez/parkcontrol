package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Abonado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAbonadoRepository extends JpaRepository<Abonado, Integer> {

    List<Abonado> findAllByNombreAndMatricula(String nombre, String matricula);

    @Query(value = "SELECT COUNT(a) > 0 FROM Abonado a WHERE a.matricula = :matricula AND a.fechaInicio <= :fecha AND a.fechaFin >= :fecha")
    Boolean existsByMatriculaAndVigente(@Param("matricula") String matricula, @Param("fecha") LocalDateTime fecha);

    Boolean existsByMatricula(String matricula);

}
