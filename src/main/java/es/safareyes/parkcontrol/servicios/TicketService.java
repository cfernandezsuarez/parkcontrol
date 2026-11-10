package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.modelos.EstadoTicket;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;

import java.time.Duration;
import java.time.LocalDateTime;

public class TicketService {

    private ITicketRepository repository;

    public void registrarEntrada(String matricula, TipoPlaza tipo) {
        LocalDateTime fechaEntrada = LocalDateTime.now();
        LocalDateTime fechaSalida = null;
        Integer minutosTotales = 0;
        EstadoTicket estadoTicket = EstadoTicket.ABIERTO;
    }

}
