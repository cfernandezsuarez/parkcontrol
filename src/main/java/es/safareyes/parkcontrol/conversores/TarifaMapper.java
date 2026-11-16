package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.CrearNuevaTarifaDTO;
import es.safareyes.parkcontrol.modelos.Tarifa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarifaMapper {

    @Mapping(target = "id", ignore = true)
    Tarifa toEntity(CrearNuevaTarifaDTO dto);

}
