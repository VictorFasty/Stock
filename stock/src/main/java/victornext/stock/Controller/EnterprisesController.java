package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Controller.Mappers.EnterprisesMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Services.EnterprisesService;

import java.util.List;

@RestController
@RequestMapping("Enterprises")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Enterprises", description = "Endpoints for managing enterprises")
public class EnterprisesController {

    private final EnterprisesService service;

    // ---------------------- CREATE ----------------------
    @Operation(summary = "Create enterprise", description = "Creates a new enterprise and returns it")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enterprise created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<EnterprisesDTO> create(@RequestBody @Valid EnterprisesDTO dto) {
        EnterprisesDTO createdDto = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }





    // ---------------------- UPDATE ----------------------
    @Operation(summary = "Update enterprise", description = "Updates an existing enterprise based on its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enterprise updated successfully"),
            @ApiResponse(responseCode = "404", description = "Enterprise not found")
    })
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<EnterprisesDTO> update(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid EnterprisesDTO dto
    ) {
        EnterprisesDTO updatedDto = service.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }





    // ---------------------- FIND ALL ----------------------
    @Operation(summary = "Find all enterprises", description = "Returns a list of all enterprises")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enterprises retrieved successfully")
    })
    @GetMapping(value = "/findall")
    public ResponseEntity<List<EnterprisesModel>> findall() {
        return service.findAll();
    }






    // ---------------------- FIND BY ID ----------------------
    @Operation(summary = "Find enterprise by ID", description = "Returns the enterprise with the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enterprise found"),
            @ApiResponse(responseCode = "404", description = "Enterprise not found")
    })
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }






    // ---------------------- DELETE ----------------------
    @Operation(summary = "Delete enterprise", description = "Deletes the enterprise with the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Enterprise deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Enterprise not found")
    })
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }






    // ---------------------- SEARCH ----------------------
    @Operation(summary = "Search enterprises by name", description = "Performs a paginated search of enterprises by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned")
    })
    @GetMapping("/search/{name}")
    public ResponseEntity<Page<EnterprisesDTO>> search(
            @PathVariable("name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "name,asc") String sort
    ) {
        return ResponseEntity.ok(service.search(name, page, pageSize));
    }
}
