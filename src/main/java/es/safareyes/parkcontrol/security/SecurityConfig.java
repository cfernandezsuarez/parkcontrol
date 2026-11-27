package es.safareyes.parkcontrol.security;

import es.safareyes.parkcontrol.servicios.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UsuarioService detallesService,
                                                PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider proveedor = new DaoAuthenticationProvider(detallesService);
        proveedor.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(proveedor);
    }

    @Bean
    SecurityFilterChain cadenaApi(HttpSecurity http,
                                  UsuarioService service,
                                  JWTService jwtService) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ==========================================
                        // 1. RUTAS PÚBLICAS (sin token)
                        // ==========================================
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Plazas (consulta pública)
                        .requestMatchers(HttpMethod.GET,
                                "/plazas/total",
                                "/plazas/ocupacion",
                                "/plazas/disponibilidad",
                                "/plazas/total/planta",
                                "/plazas/planta/{planta}"
                        ).permitAll()

                        // Tarifa vigente (consulta pública)
                        .requestMatchers(HttpMethod.GET, "/tarifas/vigente").permitAll()

                        // ==========================================
                        // 2. RUTAS DE ADMIN
                        // ==========================================
                        .requestMatchers(HttpMethod.GET,
                                "/plazas"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/plazas",
                                "/tarifas"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/tarifas/*",
                                "/abonados/*"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/tarifas/*",
                                "/abonados/*"
                        ).hasRole("ADMIN")

                        .requestMatchers("/abonados/**").hasRole("ADMIN")
                        .requestMatchers("/informes/**").hasRole("ADMIN")

                        // ==========================================
                        // 3. RUTAS DE ADMIN Y OPERADOR
                        // ==========================================
                        .requestMatchers(HttpMethod.PATCH,
                                "/plazas/*/estado"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.POST,
                                "/tickets/entrada"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.GET,
                                "/tickets/*/importe"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.POST,
                                "/tickets/*/descuentos"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.POST,
                                "/tickets/pagar"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        .requestMatchers(HttpMethod.GET,
                                "/promociones"
                        ).hasAnyRole("ADMIN", "OPERADOR")

                        // ==========================================
                        // 4. RUTAS DE ADMIN, OPERADOR Y ABONADO
                        // ==========================================
                        // Listado de tickets (paginado, filtrado)
                        .requestMatchers(HttpMethod.GET, "/tickets")
                        .hasAnyRole("ADMIN", "OPERADOR", "ABONADO")

                        // Detalle de un ticket
                        .requestMatchers(HttpMethod.GET, "/tickets/*")
                        .hasAnyRole("ADMIN", "OPERADOR", "ABONADO")

                        // ==========================================
                        // 5. RESTO: CUALQUIER USUARIO AUTENTICADO
                        // ==========================================
                        .anyRequest().authenticated())
                .addFilterBefore(
                        new JWTFilter(jwtService, service),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}