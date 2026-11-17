package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.response.DescuentoDetalleDTO;
import es.safareyes.parkcontrol.dtos.response.TicketAbonadoResponse;
import es.safareyes.parkcontrol.dtos.response.TicketDetalleResponse;
import es.safareyes.parkcontrol.modelos.Promocion;
import es.safareyes.parkcontrol.modelos.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TicketMapper {

    @Mapping(target = "numeroPlaza", source = "plaza.numero")
    TicketAbonadoResponse toAbonadoDto(Ticket ticket);

    @Mapping(target = "numeroPlaza", source = "plaza.numero")
    @Mapping(target = "descuentos", source = "promociones")
    TicketDetalleResponse toDetalleDto(Ticket ticket);

    @Mapping(target = "nombrePromocion", source = "nombre")
    @Mapping(target = "minutosDescontados", source = "minDescuento")
    DescuentoDetalleDTO toDescuentoDto(Promocion promocion);
}
