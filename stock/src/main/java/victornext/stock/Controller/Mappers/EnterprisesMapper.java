package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Model.EnterprisesModel;

@Mapper(componentModel = "spring")
public interface EnterprisesMapper {
    EnterprisesModel toEntity(EnterprisesDTO dto);

    EnterprisesDTO toDTO(EnterprisesModel enterprises);
}
