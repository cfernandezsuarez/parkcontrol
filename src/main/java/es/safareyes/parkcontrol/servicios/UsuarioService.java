package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.UsuarioMapper;
import es.safareyes.parkcontrol.dtos.UsuarioDTO;
import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.exceptions.ReglaNegocio;
import es.safareyes.parkcontrol.modelos.Rol;
import es.safareyes.parkcontrol.modelos.Usuario;
import es.safareyes.parkcontrol.repositorios.IUsuarioRepository;
import es.safareyes.parkcontrol.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private IUsuarioRepository repository;
    private UsuarioMapper mapper;
    private JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByNombreIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encuentra el usuario."));
    }

    public void registroUsuario(UsuarioDTO dto) {

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new ReglaNegocio("Las contraseñas no coinciden.");
        }

        if (repository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new ReglaNegocio("Ya existe un usuario con ese email.");
        }

        Usuario usuario = mapper.toEntity(dto);
        usuario.setRol(Rol.ABONADO);
        usuario.setContrasenya(passwordEncoder.encode(dto.password()));

        repository.save(usuario);
    }

    public LoginResponse login(LoginRequest dto) {

        if (!repository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new ReglaNegocio("No existe un usuario con ese email.");
        }

        Usuario usuario = repository
                .findByNombreIgnoreCase(dto.nombre()).get();

        if(!passwordEncoder.matches(dto.password(), usuario.getPassword())) {
            throw new ReglaNegocio("Contraseña errónea");
        }

        String token = jwtService.generar(usuario);

        return new LoginResponse(token);

    }

}
