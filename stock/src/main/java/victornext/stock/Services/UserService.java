package victornext.stock.Services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.DTOS.UserDTO;
import victornext.stock.Controller.Mappers.UserMapper;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public UserDTO create(UserDTO dto) {

        UserModel model = mapper.toEntity(dto);


        if(repository.existsByLoginAndEmail(model.getLogin(), model.getEmail())){
            throw new DuplicatedException("Usuario Ja Existente");
        }


        model.setPassword(encoder.encode(model.getPassword()));

        UserModel savedUser = repository.save(model);
        return mapper.toDto(savedUser);
    }

    public ResponseEntity<?> update(UserModel user) {
        if(repository.existsByLoginAndEmail(user.getLogin(), user.getEmail())){
            throw new RuntimeException("Usuario Ja Existente");
        }
        repository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS!");
    }

    public ResponseEntity<?> delete(Long id) {
        if(repository.findById(id).isEmpty()) {
            throw new RuntimeException("Usuario nao localizado");

        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS!");
    }

    public ResponseEntity<Object> findById(Long id) {
        if(repository.findById(id).isEmpty()) {
            throw new RuntimeException("Usuario nao localizado");

        }
        Optional<UserModel> user = repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }

    public Optional<UserModel> findByEmail(String email) {
        if(repository.findByEmail(email).isEmpty()){
            throw new RuntimeException("Email nao encontrado");
        }
        return repository.findByEmail(email);
    }

    public ResponseEntity<List<UserModel>> findALl() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }


    public UserModel FindByLogin(String login){
        return repository.findByLogin(login);
    }
}
