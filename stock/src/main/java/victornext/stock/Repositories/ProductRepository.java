package victornext.stock.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import victornext.stock.Model.ProductModel;


public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
