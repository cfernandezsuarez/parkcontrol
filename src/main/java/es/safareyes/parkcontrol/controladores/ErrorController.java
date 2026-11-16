package es.safareyes.parkcontrol.controladores;

import es.safareyes.parkcontrol.exceptions.ElementoNoEncontrado;
import es.safareyes.parkcontrol.exceptions.MatriculaInvalida;
import es.safareyes.parkcontrol.exceptions.PromocionRepetida;
import es.safareyes.parkcontrol.exceptions.SinPlazasLibres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(ElementoNoEncontrado.class)
    public ProblemDetail noEncontrado(ElementoNoEncontrado e) {
        return construir(HttpStatus.NOT_FOUND, "Recurso no encontrado", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacion(MethodArgumentNotValidException e) {
        Map<String, String> errores = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        ProblemDetail problema = construir(HttpStatus.BAD_REQUEST,
                "Datos no válidos", "Revisa los campos indicados");
        problema.setProperty("errores", errores);
        return problema;
    }

    @ExceptionHandler(MatriculaInvalida.class)
    public ProblemDetail matriculaInvalida(MatriculaInvalida e) {
        return construir(HttpStatus.valueOf(409), "Regla de negocio no cumplida", e.getMessage());
    }

    @ExceptionHandler(SinPlazasLibres.class)
    public ProblemDetail plazasLibres(SinPlazasLibres e) {
        return construir(HttpStatus.valueOf(422), "Regla de negocio no cumplida", e.getMessage());
    }

    @ExceptionHandler(PromocionRepetida.class)
    public ProblemDetail promocionRepetida(PromocionRepetida e) {
        return construir(HttpStatus.valueOf(409), "Regla de negocio no cumplida", e.getMessage());
    }

    private ProblemDetail construir(HttpStatus estado, String titulo, String detalle) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(estado, detalle);
        problema.setTitle(titulo);
        problema.setProperty("timestamp", LocalDateTime.now());
        return problema;
    }
}
