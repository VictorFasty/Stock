package victornext.stock.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victornext.stock.Controller.DTOS.ProductDTO;
import victornext.stock.Controller.Mappers.ProductMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Services.ProductService;
import victornext.stock.Model.ProductModel;

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

}
