package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Model.ClientModel;
import victornext.stock.Model.EnterprisesModel;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "clientSecret", target = "clientSecret")
    @Mapping(source = "redirectURI", target = "redirectURI")
    @Mapping(source = "scope", target = "scope")
    ClientModel toEntity(ClientDTO dto);

    ClientDTO toDTO(ClientModel model);
}
