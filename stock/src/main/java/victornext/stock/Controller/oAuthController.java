package victornext.stock.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.LoginRequestDTO;
import victornext.stock.Controller.DTOS.RegisterRequestDTO;
import victornext.stock.Controller.DTOS.ResponseDTO;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.infra.security.TokenService;

import java.util.Optional;

@RestController 
@RequestMapping("/auth")
@RequiredArgsConstructor
public class oAuthController {


    private AuthenticationManager auhtenAuthenticationManager;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = this.auhtenAuthenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new ResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        if(repository.findByEmail(body.email()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        UserModel newUser = new UserModel(body.name(), body.email(), encryptedPassword, body.role());

        repository.save(newUser);

        return ResponseEntity.ok("Registration completed successfully!");
    }
}
