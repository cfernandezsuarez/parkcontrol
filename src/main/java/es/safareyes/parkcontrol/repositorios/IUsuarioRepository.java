package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombre(String nombre);

    List<Usuario> nombre(String nombre);

    @Query(value = "SELECT u.id_abonado FROM parkcontrol.usuario u WHERE nombre = :nombre", nativeQuery = true)
    Integer findIdAbonadoByNombre(@Param("nombre") String nombre);
}
