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
import victornext.stock.Enums.UserRoles;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.infra.security.TokenService;

import java.util.Optional;

@RestController 
@RequestMapping("/auth")
@RequiredArgsConstructor
public class oAuthController {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
  
        UserModel user = this.repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found!"));


        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {

        Optional<UserModel> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));

            try {
                newUser.setRole(body.role());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role provided");
            }

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().body("Email already in use");
    }
}