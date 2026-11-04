package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.modelos.Usuario;
import es.safareyes.parkcontrol.servicios.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UsuarioService service;
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<?>login(@RequestBody LoginRequest request) {
        Usuario usuario = service.consultarUsuarioPorNombre(request.getEmail());

        return ResponseEntity.ok(new LoginResponse("Hola"));
    }

}
