package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.RegistroRequest;
import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.modelos.Usuario;
import es.safareyes.parkcontrol.servicios.RegistroService;
import es.safareyes.parkcontrol.servicios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistroService registroService;
    private UsuarioService service;

    public AuthController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest request) {
        Usuario usuario = service.consultarUsuarioPorNombre(request.getEmail());

        return ResponseEntity.ok(new LoginResponse("Hola"));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest request) {
        registroService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
