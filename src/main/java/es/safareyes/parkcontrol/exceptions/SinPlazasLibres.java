package es.safareyes.parkcontrol.exceptions;

public class SinPlazasLibres extends RuntimeException {
    public SinPlazasLibres(String message) {
        super(message);
    }
}
