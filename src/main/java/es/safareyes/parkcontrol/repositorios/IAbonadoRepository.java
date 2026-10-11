package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Abonado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAbonadoRepository extends JpaRepository<Abonado, Integer> {

    List<Abonado> findAllByNombreAndMatricula(String nombre, String matricula);

}
