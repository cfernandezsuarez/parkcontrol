package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.response.TicketAbonadoResponse;
import es.safareyes.parkcontrol.modelos.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TicketMapper {

    @Mapping(target = "numeroPlaza", source = "plaza.numero")
    TicketAbonadoResponse toDto(Ticket ticket);

}
