package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorios {

    @Autowired
    private IPlazaRepository plazaRepository;

    @Autowired
    private ITarifaRepositorio tarifaRepositorio;

    @Autowired
    private ITicketRepository ticketRepository;

    @Autowired
    private IAbonadoRepository abonadoRepository;

    @Autowired
    private IPromocionRepository promocionRepository;

    // GET 1
    @Test
    public void listadoPlantaTipoEstado() {

        List<Plaza> listaPlazas = plazaRepository.findAllByPlantaAndTipoAndEstado(1, TipoPlaza.NORMAL, false);

    }

    // GET 2
    @Test
    public void disponiblePlantaTipo() {

        List<Plaza> listaPlazas = plazaRepository.plazasLibresPorPlantaTipo(1, TipoPlaza.NORMAL);

    }

    // GET 5
    @Test
    public void tarifaDisponible() {

        List<Tarifa> tarifasVigor = tarifaRepositorio.tarifaVigor();

    }

    // GET 8
    @Test
    public void importeTicketEstado() {

        List<ImporteTicket> listaTickets = ticketRepository.importeTicketAbierto("1234ABC");

    }

    // GET 11
    @Test
    public void listadoTicketPorAbonado() {

        List<Abonado> abonado = abonadoRepository.findAllByNombreAndMatricula("Ana López", "1234ABC");
        Abonado abonado1 = abonado.getFirst();

        List<Ticket> tickets = ticketRepository.ticketsPorAbonado(abonado1, EstadoTicket.ABIERTO, BigDecimal.valueOf(2), BigDecimal.valueOf(4), 90, 90);

    }

    // GET 12
    @Test
    public void descuentosAplicados() {

        List<Ticket> tickets = ticketRepository.findAllById(1);

    }

    // GET 13
    @Test
    public void catalogoPromociones() {

        List<Promocion> promociones = promocionRepository.catalogoPromociones();

    }
}
