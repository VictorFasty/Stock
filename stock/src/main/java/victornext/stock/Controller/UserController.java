package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.UserDTO;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;


@Tag(name = "User Controller")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided data in the request body"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Conflict: user already exists")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO dto) {
        return service.create(dto);
    }




    @Operation(
            summary = "List all users",
            description = "Returns a list of all users in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping("/findAll")
    public ResponseEntity<Page<UserModel>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return service.findALl(pageable);
    }




    @Operation(
            summary = "Find user by ID",
            description = "Returns a single user based on the given ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }






    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user from the system based on the given ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }




}
