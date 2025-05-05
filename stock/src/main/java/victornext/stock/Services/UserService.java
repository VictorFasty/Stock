package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository repository;
    private final PasswordEncoder encoder;



    public ResponseEntity<?> create(UserModel user) {
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));

        UserModel savedUser = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("SUCESS!!");
    }


    public ResponseEntity<?> update(UserModel user){
        UserModel model = repository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("SUCESS!!");
    }


    public ResponseEntity<?> delete(Long id) {
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("SUCESS!!");
    }


    public ResponseEntity<?> findById(Long id) {
        repository.findById(id);


        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }


}
