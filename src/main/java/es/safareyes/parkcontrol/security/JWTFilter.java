package es.safareyes.parkcontrol.security;

import es.safareyes.parkcontrol.servicios.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final String PREFIJO = "Bearer ";
    private final JWTService jwtService;
    private final UsuarioService usuarioService;
    @Override
    protected void doFilterInternal(HttpServletRequest peticion,
                                    HttpServletResponse respuesta,
                                    FilterChain cadena) throws ServletException, IOException {
        String cabecera = peticion.getHeader(HttpHeaders.AUTHORIZATION);
        if (cabecera == null || !cabecera.startsWith(PREFIJO)) {
            cadena.doFilter(peticion, respuesta);
            return;
        }
        try {
            Jwt token = jwtService.validar(cabecera.substring(PREFIJO.length()));
            UserDetails detalles = usuarioService.loadUserByUsername(token.getSubject());

            var autenticacion = UsernamePasswordAuthenticationToken.authenticated(
                    detalles, null, detalles.getAuthorities());
            autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(peticion));
            SecurityContext contexto = SecurityContextHolder.createEmptyContext();
            contexto.setAuthentication(autenticacion);
            SecurityContextHolder.setContext(contexto);
        } catch (JwtException | UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            logger.debug("Token rechazado: " + e.getMessage());
        }
        cadena.doFilter(peticion, respuesta);
    }

}
