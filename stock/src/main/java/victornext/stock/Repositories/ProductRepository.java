package victornext.stock.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Model.ProductModel;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductModel, Long>, JpaSpecificationExecutor<ProductModel> {
    Optional<ProductModel> findByName(String name);


}
