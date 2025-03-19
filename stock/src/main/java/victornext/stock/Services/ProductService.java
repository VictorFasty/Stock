package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;
import victornext.stock.Repositories.Specs.EnterprisesSpecs;
import victornext.stock.Repositories.Specs.ProductSpecs;

import java.util.List;
import java.util.Optional;


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


    public ResponseEntity<String> delete(Long id) {
        Optional<ProductModel> productOptional = repository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for deletion");
        }

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully deleted");
    }


    public ResponseEntity<Object> findById(Long id) {
        repository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }



    public List<ProductModel> Search(String name) {
        Specification<ProductModel> specs = Specification.where((root, query, cb) -> cb.conjunction()); // Inicia com uma condição vazia

        if (name != null && !name.isEmpty()) {
            System.out.println("Aplicando filtro para nome: " + name); // Log de depuração
            specs = specs.and(ProductSpecs.nameLike(name)); // Adiciona o filtro de nome
        }


        return repository.findAll(specs); // Busca todos os produtos com a Specification aplicada
    }


}
