package victornext.stock.Controller.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import victornext.stock.Controller.DTOS.FindEnterpriseDTO;
import victornext.stock.Controller.DTOS.ProductDTO;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.EnterprisesRepository;

@Mapper(componentModel = "spring", uses = EnterprisesMapper.class)
public interface ProductMapper {

    @Mapping(target = "enterprise", expression = "java(repository.findById(dto.idEnterprise()).orElse(null))")
    public abstract ProductModel toEntity(ProductDTO dto);


    public abstract ProductDTO toDTO(ProductModel productModel);

    public abstract FindEnterpriseDTO toFindDTO(ProductModel productModel);
}


