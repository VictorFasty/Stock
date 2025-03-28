package victornext.stock.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Controller.DTOS.FindEnterpriseDTO;
import victornext.stock.Controller.DTOS.ProductDTO;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Services.ProductService;
import victornext.stock.Model.ProductModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;



    @PostMapping(value = "/create")
    ResponseEntity<?> create(@RequestBody @Valid ProductDTO dto) {
        ProductModel model = mapper.toEntity(dto);

        ResponseEntity<?> savedProduct = service.create(model);

        //  201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    @PutMapping(value = "/update/{id}")
    ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProductDTO DTO) {
        ProductModel model = mapper.toEntity(DTO);
        model.setId(id);

        return service.update(model);
    }



    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @GetMapping(value = "/find/{id}")
    ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }



    @GetMapping("/search/{name}")
    public ResponseEntity<List<FindEnterpriseDTO>> search(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "name,asc") String sort) {

        List<ProductModel> searchResults = service.Search(name, page, pageSize);
        List<FindEnterpriseDTO> dtoResults = searchResults.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtoResults);
    }


    @PutMapping("A/{id}/{quantity}")
    public ResponseEntity<Object> AdditionQuantity(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "quantity") Integer quantity
    ) {
        return service.AdditionProduct(id, quantity);
    }

    @PutMapping("R/{id}/{quantity}")
    public ResponseEntity<Object> RemoveQuantity(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "quantity") Integer quantity
    ) {
        return service.RemoveProduct(id, quantity);
    }
}
