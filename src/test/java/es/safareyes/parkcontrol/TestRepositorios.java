package es.safareyes.parkcontrol;

import es.safareyes.parkcontrol.modelos.Tarifa;
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
    private ITarifaRepositorio repository;

    @Test
    public void TestRepositorios() {

        List<Tarifa> listaTarifas = repository.findAll();

    }
}
