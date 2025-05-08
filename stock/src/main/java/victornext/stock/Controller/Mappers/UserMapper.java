package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import victornext.stock.Controller.DTOS.UserDTO;
import victornext.stock.Model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel toEntity(UserDTO dto);


    UserDTO toDTO(UserModel model);
}
