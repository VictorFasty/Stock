package victornext.stock.Controller;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victornext.stock.Controller.DTOS.LoginRequestDTO;
import victornext.stock.Controller.DTOS.LoginResponseDTO;
import victornext.stock.Model.User;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.Services.TokenService;

@RestController
@RequestMapping("auth")
@Data
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;


    @PostMapping("/login")
    private ResponseEntity login(@RequestBody @Valid LoginRequestDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
