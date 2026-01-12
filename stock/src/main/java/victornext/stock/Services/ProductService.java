package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;




    public ProductModel create(ProductModel model) {
        if (repository.existsByNameIgnoreCase(model.getName())) {
            throw new DuplicatedException("Product with name '" + model.getName() + "' already exists.");
        }
        return repository.save(model);
    }


    public ResponseEntity<?> update(ProductModel model) {
        if (repository.existsByNameIgnoreCase(model.getName())) {
            throw new DuplicatedException("Product with name '" + model.getName() + "' already exists.");
        }
        ProductModel model1 = repository.save(model);

        return ResponseEntity.status(HttpStatus.OK).body(model1);
    }


    public ResponseEntity<String> delete(Long id) {
        if(repository.findById(id) == null) {
            throw new RuntimeException("Nao encontrado");
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product successfully deleted");
    }


    public ResponseEntity<Object> findById(Long id) {
        if(repository.findById(id) == null) {
            throw new RuntimeException("Nao encontrado");
        }
        repository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }


    public List<ProductModel> Search(String name, Integer page, Integer pageSize) {
        if (name == null || name.isEmpty()) {
            throw new NotFoundException("O nome para pesquisa não pode ser vazio ou nulo.");
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);


        Page<ProductModel> pageResult = repository.findByNameContainingIgnoreCase(name, pageRequest);

        return pageResult.getContent();
    }


    public ResponseEntity<Object> AdditionProduct(Long id, Integer quantity) {
        ProductModel model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found!!"));

        Integer stock = model.getQuantity();
        model.setQuantity(stock + quantity);

        repository.save(model);
        return ResponseEntity.ok(model);

    }

    public ResponseEntity<Object> RemoveProduct(Long id, Integer quantity) {
        if(repository.findById(id) == null) {
            throw new RuntimeException("Nao encontrado");
        }
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
