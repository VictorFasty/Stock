package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.ProductDTO;
import victornext.stock.Services.ProductService;
import victornext.stock.Model.ProductModel;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProductController {
    private final ProductService service;



    @Operation(summary = "Create a new product", description = "Registers a new product in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ProductDTO dto) {
        return service.create(dto);
    }




    @Operation(summary = "Update product", description = "Updates an existing product based on ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }






    @Operation(summary = "Delete product", description = "Deletes a product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }






    @Operation(summary = "Find product by ID", description = "Returns product details based on ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }



    @Operation(summary = "Find All Products", description = "Return all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return All Products")
    })
    @GetMapping("/findAll")
    public ResponseEntity<Page<ProductModel>> findAll(
           @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }



    @Operation(summary = "Search products by name", description = "Returns a paginated list of products that match the name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned successfully")
    })
    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> search(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(service.search(name, page, pageSize));
    }







    @Operation(summary = "Add quantity to product", description = "Adds a specified quantity to an existing product's stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity added successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/add-stock/{id}/{quantity}")
    public ResponseEntity<ProductDTO> addStock(@PathVariable Long id, @PathVariable Integer quantity) {
        return ResponseEntity.ok(service.addStock(id, quantity));
    }






    @Operation(summary = "Remove quantity from product", description = "Removes a specified quantity from an existing product's stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity removed successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/remove-stock/{id}/{quantity}")
    public ResponseEntity<ProductDTO> removeStock(@PathVariable Long id, @PathVariable Integer quantity) {
        return ResponseEntity.ok(service.removeStock(id, quantity));
    }
}
