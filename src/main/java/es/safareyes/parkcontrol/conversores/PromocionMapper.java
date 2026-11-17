package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.response.PromocionResponse;
import es.safareyes.parkcontrol.modelos.Promocion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PromocionMapper {

    PromocionResponse toPromocionDto(Promocion promocion);

}
