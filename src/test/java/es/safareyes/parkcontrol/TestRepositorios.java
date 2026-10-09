package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.dtos.ImporteTicket;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.IAbonadoRepository;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import es.safareyes.parkcontrol.repositorios.ITicketRepository;
import org.hibernate.query.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
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

        List<ImporteTicket> listaTickets = ticketRepository.importeTicketAbierto("4321KBB");

    }

    // GET 11
    @Test
    public void listadoTicketPorAbonado() {

        List<Ticket> listaTicketAbonado = ticketRepository.listadoTicketPorAbonado(1);

    }
}
