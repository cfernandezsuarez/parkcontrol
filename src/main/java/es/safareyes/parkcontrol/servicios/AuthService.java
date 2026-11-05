package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.security.JwtService;
import es.safareyes.parkcontrol.security.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNombre(), request.getPassword())
        );

        UserDetails user = usuarioDetailsService.loadUserByUsername(request.getNombre());

        String token = jwtService.generarToken(user);

        return new LoginResponse(token);
    }

}
