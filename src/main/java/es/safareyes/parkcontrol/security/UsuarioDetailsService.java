package es.safareyes.parkcontrol.security;

import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        return usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombre));
    }

}
