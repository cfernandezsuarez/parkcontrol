package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.modelos.Usuario;
import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private IUsuarioRepository repository;

    public Usuario consultarUsuarioPorNombre(String nombre) {
        return repository.findByNombre(nombre)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombre));
    }

}
