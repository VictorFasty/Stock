package victornext.stock.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public class oAuthControlle {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        UserModel user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found!"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();


    }




    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<UserModel> user = this.repository.findByEmail(body.email());
        if(user.isEmpty()){
            UserModel newUser = new UserModel();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);


                String token = this.tokenService.generateToken(newUser);
                return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));


        }

        return ResponseEntity.badRequest().build();


    }
}
