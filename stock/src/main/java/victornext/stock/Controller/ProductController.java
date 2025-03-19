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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;



    @PostMapping(value = "/create")
    ResponseEntity<?> create(@RequestBody @Valid ProductDTO dto) {
        // Mapeando o DTO para o modelo
        ProductModel model = mapper.toEntity(dto);

        // Chamando o serviço para salvar o modelo no banco de dados
        ResponseEntity<?> savedProduct = service.create(model);

        // Retornando o modelo salvo com status 201 (CREATED)
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
            @PathVariable(value = "name") String name) {

        List<ProductModel> searchResults = service.Search(name);
        List<FindEnterpriseDTO> dtoResults = searchResults.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtoResults);
    }

}
