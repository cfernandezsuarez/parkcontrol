package es.safareyes.parkcontrol.security;

import es.safareyes.parkcontrol.modelos.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JWTService {

    private final JwtEncoder codificador;
    private final JwtDecoder decodificador;
    private final long minutos;
    private final String emisor;

    public JWTService(@Value("${jwt.secreto}") String secreto,
                      @Value("${jwt.minutos}") long minutos,
                      @Value("${jwt.emisor}") String emisor) {
        SecretKey clave = new SecretKeySpec(secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.codificador = NimbusJwtEncoder.withSecretKey(clave)
                .algorithm(MacAlgorithm.HS256)
                .build();
        this.decodificador = NimbusJwtDecoder.withSecretKey(clave)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        this.minutos = minutos;
        this.emisor = emisor;
    }

    public String generar(Usuario usuario) {
        Instant ahora = Instant.now();
        JwtClaimsSet contenido = JwtClaimsSet.builder()
                .issuer(emisor)
                .issuedAt(ahora)
                .expiresAt(ahora.plus(minutos, ChronoUnit.MINUTES))
                .subject(usuario.getNombre())
                .claim("rol", usuario.getRol().name())
                .claim("nombre", usuario.getNombre())
                .build();
        return codificador.encode(JwtEncoderParameters.from(contenido)).getTokenValue();
    }

    public Jwt validar(String token) {
        return decodificador.decode(token);
    }

}
