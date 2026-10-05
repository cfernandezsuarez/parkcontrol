package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.modelos.Plaza;
import es.safareyes.parkcontrol.modelos.Tarifa;
import es.safareyes.parkcontrol.modelos.TipoPlaza;
import es.safareyes.parkcontrol.repositorios.IPlazaRepository;
import es.safareyes.parkcontrol.repositorios.ITarifaRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositorios {

    @Autowired
    private IPlazaRepository plazaRepository;

    @Test
    public void listadoPlantaTipoEstado() {

        List<Plaza> listaPlazas = plazaRepository.findByPlantaAndTipoAndEstado(1, TipoPlaza.ELECTRICA, true);

    }

    @Test
    public void disponiblePlantaTipo() {

        List<Plaza> listaPlazas = plazaRepository.plazasLibresPorPlantaTipo(1, TipoPlaza.ELECTRICA);

    }
}
