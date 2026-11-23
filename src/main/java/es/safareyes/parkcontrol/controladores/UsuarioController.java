package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.dtos.UsuarioDTO;
import es.safareyes.parkcontrol.dtos.request.LoginRequest;
import es.safareyes.parkcontrol.dtos.response.LoginResponse;
import es.safareyes.parkcontrol.servicios.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UsuarioController {

    private UsuarioService service;

    @PostMapping("/registro")
    public void registrarUsuario(@RequestBody UsuarioDTO dto) {
        service.registroUsuario(dto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest dto) {
        return service.login(dto);
    }

}
