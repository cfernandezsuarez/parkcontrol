package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.dtos.*;
import es.safareyes.parkcontrol.modelos.*;
import es.safareyes.parkcontrol.repositorios.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

        Page<Plaza> listaPlazas = plazaRepository.findAllByPlantaAndTipoAndEstado(0, TipoPlaza.NORMAL, EstadoPlaza.LIBRE, PageRequest.of(0, 10));

    }

    // GET 2
    @Test
    public void disponiblePlantaTipo() {

        List<Plaza> listaPlazas = plazaRepository.plazasLibresPorPlantaTipo(0, TipoPlaza.NORMAL);

    }

    // GET 5
    @Test
    public void tarifaDisponible() {

        Optional<Tarifa> tarifasVigor = tarifaRepositorio.tarifaVigente(LocalDateTime.now());

    }

    // GET 8
    @Test
    public void importeTicketEstado() {

        List<ImporteTicket> listaTickets = ticketRepository.importeTicketAbierto("1234ABC");

    }

    // GET 11
    @Test
    public void listadoTicketPorAbonado() {

        List<Abonado> abonados = abonadoRepository.findAllByNombreAndMatricula("Ana López", "1234ABC");
        Abonado abonado = abonados.getFirst();

        Page<Ticket> tickets = ticketRepository.ticketsPorAbonado(abonado, EstadoTicket.ABIERTO, BigDecimal.valueOf(2), BigDecimal.valueOf(4), 90, 90, PageRequest.of(0, 10));

    }

    // GET 12
    @Test
    public void descuentosAplicados() {

        List<DetalleTicketDescuento> ticketsDescuentos = ticketRepository.ticketsDescuentos(1);

    }

    // GET 13
    @Test
    public void catalogoPromociones() {

        List<Promocion> promociones = promocionRepository.catalogoPromociones();

    }

    // GET 15
    @Test
    public void recaudacionDia() {

        List<RecaudacionDia> recaudacion = ticketRepository.recaudacionPorDia(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2024, 1, 1, 23, 59));

    }

    // GET EXTRA
    @Test
    public void ocupacionTotales() {

        Long ocupacionTotal = plazaRepository.count();

    }

    // GET EXTRA
    @Test
    public void plazasPorEstado() {

        Long plazas = plazaRepository.countPlazaByEstado(EstadoPlaza.LIBRE);

    }
}
