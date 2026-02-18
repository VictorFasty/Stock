package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.DTOS.ProductDTO;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;


    public ResponseEntity<?> create(ProductDTO dto) {
        ProductModel savedModel = mapper.toEntity(dto);
        repository.save(savedModel);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public ProductDTO update(Long id, ProductDTO dto) {
        ProductModel existingModel = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));


        repository.findByName(dto.name())
                .filter(p -> !p.getId().equals(id))
                .ifPresent(p -> { throw new DuplicatedException("Name already exists"); });


        ProductModel inputModel = mapper.toEntity(dto);
        inputModel.setId(id);



        return mapper.toDTO(repository.save(inputModel));
    }


    public ResponseEntity<?> delete(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao localizado");
        }
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public ProductDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    public List<ProductDTO> search(String name, Integer page, Integer pageSize) {
        if (name == null || name.isBlank()) {
            throw new NotFoundException("Search name cannot be empty.");
        }
        Pageable pageRequest = PageRequest.of(page, pageSize);
        return repository.findByNameContainingIgnoreCase(name, pageRequest)
                .map(mapper::toDTO)
                .getContent(); // .getContent() pega a lista da Page
    }


    public ProductDTO addStock(Long id, Integer quantity) {
        ProductModel model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        model.setQuantity(model.getQuantity() + quantity);
        return mapper.toDTO(repository.save(model));
    }

    public ProductDTO removeStock(Long id, Integer quantity) {
        ProductModel model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (model.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock!");
        }

        model.setQuantity(model.getQuantity() - quantity);
        return mapper.toDTO(repository.save(model));
    }

    public Page<ProductModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}