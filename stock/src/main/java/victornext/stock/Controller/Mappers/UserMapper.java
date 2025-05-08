package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import victornext.stock.Controller.DTOS.UserDTO;
import victornext.stock.Model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserModel model);

    @Mapping(target = "role", source = "role") // garante que o enum seja mapeado corretamente
    UserModel toEntity(UserDTO dto);
}
