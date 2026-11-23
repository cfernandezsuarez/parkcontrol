package es.safareyes.parkcontrol.conversores;

import es.safareyes.parkcontrol.dtos.UsuarioDTO;
import es.safareyes.parkcontrol.modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "password", ignore = true)
    Usuario toEntity(UsuarioDTO dto);

}
