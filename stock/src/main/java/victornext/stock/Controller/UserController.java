package victornext.stock.Controller;

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

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody @Valid UserDTO dto){
        UserModel model = mapper.toEntity(dto);

        ResponseEntity<?> savedUser = service.create(model);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PutMapping(value = "/update/{id}")
    ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO dto){
        UserModel model = mapper.toEntity(dto);
        model.setId(id);

        return service.update(model);
    }


    @GetMapping(value = "/findall")
    public ResponseEntity<List<UserModel>> findall(){
        return service.findALl();
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id")Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable (value = "id") Long id){
        return service.delete(id);
    }


}
