package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.request.RegistroRequest;
import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.servicios.AuthService;
import es.safareyes.parkcontrol.servicios.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RegistroService registroService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registro(@Valid @RequestBody RegistroRequest request) {
        registroService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
