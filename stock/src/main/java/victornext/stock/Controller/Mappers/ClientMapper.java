package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Model.ClientModel;
import victornext.stock.Model.EnterprisesModel;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientModel toEntity(ClientDTO dto);

    ClientDTO toDTO(ClientModel model);
}
