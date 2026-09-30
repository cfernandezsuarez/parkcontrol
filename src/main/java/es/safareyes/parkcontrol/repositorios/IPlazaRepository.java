package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Plaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlazaRepository extends JpaRepository<Plaza, Integer> {
}
