package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.response.AbonadoResponse;
import es.safareyes.parkcontrol.modelos.Abonado;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AbonadoMapper {

    AbonadoResponse toAbonadoDto(Abonado abonado);

}