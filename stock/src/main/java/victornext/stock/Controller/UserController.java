package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.UserDTO;
import victornext.stock.Controller.Mappers.UserMapper;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;

import java.util.List;


@Tag(name = "User Controller")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;


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
    ResponseEntity<?> create(@RequestBody @Valid UserDTO dto){
        UserModel model = mapper.toEntity(dto);

        ResponseEntity<?> savedUser = service.create(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }




    @Operation(
            summary = "List all users",
            description = "Returns a list of all users in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping(value = "/findall")
    public ResponseEntity<List<UserModel>> findAll() {
        return service.findALl();
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
