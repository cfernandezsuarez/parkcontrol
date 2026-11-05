package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.request.RegistroRequest;
import es.safareyes.parkcontrol.modelos.Rol;
import es.safareyes.parkcontrol.modelos.Usuario;
import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void registrar(RegistroRequest request) {
        if (usuarioRepository.findByNombre(request.nombre()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setContrasenya(passwordEncoder.encode(request.password()));
        usuario.setRol(Rol.ABONADO);

        usuarioRepository.save(usuario);
    }
}
