package es.safareyes.parkcontrol.servicios;

import es.safareyes.parkcontrol.conversores.PromocionMapper;
import es.safareyes.parkcontrol.dtos.response.PromocionResponse;
import es.safareyes.parkcontrol.repositorios.IPromocionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PromocionService {

    private IPromocionRepository repository;

    private PromocionMapper mapper;

    public List<PromocionResponse> obtenerCatalogo() {
        return repository.catalogoPromociones().stream().map(p -> mapper.toPromocionDto(p)).toList();
    }

}
