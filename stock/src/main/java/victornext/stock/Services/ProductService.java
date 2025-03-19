package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;




    public ResponseEntity<ProductModel> create(ProductModel product) {
        // Salvando o produto no banco de dados
        ProductModel savedProduct = repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    public ResponseEntity<?> update(ProductModel model) {
        ProductModel model1 = repository.save(model);

        return ResponseEntity.status(HttpStatus.OK).body(model1);
    }


    public ResponseEntity<?> delete(Long id) {
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
