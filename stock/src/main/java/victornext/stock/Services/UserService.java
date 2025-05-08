package victornext.stock.Services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.validators.UserValidator;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserValidator validator;
    private final PasswordEncoder encoder;

    public ResponseEntity<?> create(UserModel user) {
        validator.validateCreate(user);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS!");
    }

    public ResponseEntity<?> update(UserModel user) {
        validator.validateUpdate(user);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS!");
    }

    public ResponseEntity<?> delete(Long id) {
        validator.validateDelete(id);
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS!");
    }

    public ResponseEntity<Object> findById(Long id) {
        validator.validateFindById(id);
        Optional<UserModel> user = repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }

    public Optional<UserModel> findByEmail(String email) {
        validator.validateEmailForSearch(email);
        return repository.findByEmail(email);
    }

    public ResponseEntity<List<UserModel>> findALl() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }


    public UserModel FindByLogin(String login){
        return repository.findByLogin(login);
    }
}
