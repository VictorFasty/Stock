package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;
import victornext.stock.Repositories.Specs.EnterprisesSpecs;
import victornext.stock.Repositories.Specs.ProductSpecs;
import victornext.stock.validators.ProductValidator;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductValidator validator;




    public ResponseEntity<ProductModel> create(ProductModel model) {
        //validation
        validator.ValidatorALL(model);
        // save
        ProductModel savedProduct = repository.save(model);
        //return http status code
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    public ResponseEntity<?> update(ProductModel model) {
        validator.ValidatorALL(model);
        ProductModel model1 = repository.save(model);

        return ResponseEntity.status(HttpStatus.OK).body(model1);
    }


    public ResponseEntity<String> delete(Long id) {
        validator.validateId(id);
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully deleted");
    }


    public ResponseEntity<Object> findById(Long id) {
        validator.validateId(id);
        repository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }


    public List<ProductModel> Search(String name, Integer page, Integer pageSize) {
        validator.validateSearchName(name); 


        Specification<ProductModel> specs = Specification.where(ProductSpecs.nameLike(name));


        Pageable pageRequest = PageRequest.of(page, pageSize);

        Page<ProductModel> pageResult = repository.findAll(specs, pageRequest);

        return pageResult.getContent();
    }


    public ResponseEntity<Object> AdditionProduct(Long id, Integer quantity) {
        validator.validateId(id);
        ProductModel model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found!!"));

        Integer stock = model.getQuantity();
        model.setQuantity(stock + quantity);

        repository.save(model);
        return ResponseEntity.ok(model);

    }

    public ResponseEntity<Object> RemoveProduct(Long id, Integer quantity) {
        validator.validateId(id);
        ProductModel model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found!!"));

        Integer stock = model.getQuantity();

        if (stock < quantity) {
            return ResponseEntity.badRequest().body("Error: Insufficient quantity in stock!");
        }

        model.setQuantity(stock - quantity);

        repository.save(model);
        return ResponseEntity.ok(model);

    }
}
