package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;



    @Operation(summary = "Create a new product", description = "Registers a new product in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody @Valid ProductDTO dto) {
        ProductModel model = mapper.toEntity(dto);
        ResponseEntity<?> savedProduct = service.create(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }





    @Operation(summary = "Update product", description = "Updates an existing product based on ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid ProductDTO dto) {
        ProductModel model = mapper.toEntity(dto);
        model.setId(id);
        return service.update(model);
    }






    @Operation(summary = "Delete product", description = "Deletes a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }






    @Operation(summary = "Find product by ID", description = "Returns product details based on ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }







    @Operation(summary = "Search products by name", description = "Returns a paginated list of products that match the name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned successfully")
    })
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







    @Operation(summary = "Add quantity to product", description = "Adds a specified quantity to an existing product's stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity added successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("A/{id}/{quantity}")
    public ResponseEntity<Object> AdditionQuantity(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "quantity") Integer quantity) {
        return service.AdditionProduct(id, quantity);
    }






    @Operation(summary = "Remove quantity from product", description = "Removes a specified quantity from an existing product's stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity removed successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("R/{id}/{quantity}")
    public ResponseEntity<Object> RemoveQuantity(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "quantity") Integer quantity) {
        return service.RemoveProduct(id, quantity);
    }
}
