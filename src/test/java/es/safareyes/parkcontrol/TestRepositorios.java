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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Page<Plaza> listaPlazas = plazaRepository.findAllByPlantaAndTipoAndEstado(1, TipoPlaza.NORMAL, EstadoPlaza.OCUPADA, PageRequest.of(0, 10));

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

        List<Abonado> abonado = abonadoRepository.findAllByNombreAndMatricula("Carlos Gómez", "1234BBB");
        Abonado abonado1 = abonado.getFirst();

        Page<Ticket> tickets = ticketRepository.ticketsPorAbonado(abonado1, EstadoTicket.ABIERTO, BigDecimal.valueOf(2), BigDecimal.valueOf(4), 90, 90, PageRequest.of(0, 10));

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

        List<RecaudacionDia> recaudacion = ticketRepository.recaudacionPorDia(LocalDateTime.of(2026, 3, 1, 0, 0), LocalDateTime.of(2026, 10, 5, 14, 0));

    }

    // GET 16
    @Test
    public void ocupacionPlanta() {

        List<OcupacionPlanta> plantasOcupadas = plazaRepository.ocupacionPlanta(EstadoTicket.ABIERTO);

    }

    // GET EXTRA
    @Test
    public void ocupacionPlazasTipo() {

        List<OcupacionPorTipo> ocupacionPorTipos = plazaRepository.opcupacionPorTipos();

    }

    // GET EXTRA
    @Test
    public void ocupacionPlazasTotal() {

        List<TotalPlazasOcupadas> ocupacionPlazasTotal = plazaRepository.totalPlazasOcupadas();

    }
}
