package es.safareyes.parkcontrol.repositorios;

import es.safareyes.parkcontrol.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByNombre(String nombre);

    List<Usuario> nombre(String nombre);
}
