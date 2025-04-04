package victornext.stock.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import victornext.stock.Controller.DTOS.LoginRequestDTO;
import victornext.stock.Controller.DTOS.RegisterRequestDTO;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.infra.security.TokenService;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class oAuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String login(LoginRequestDTO body, Model model) {
        UserModel user = repository.findByEmail(body.email()).orElse(null);

        if (user != null && passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            model.addAttribute("name", user.getName());
            model.addAttribute("token", token);
            return "home"; 
        }

        model.addAttribute("error", "Invalid credentials!");
        return "login";
    }



    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @PostMapping("/register")
    public String register(RegisterRequestDTO body, Model model) {
        Optional<UserModel> existingUser = repository.findByEmail(body.email());

        if (existingUser.isEmpty()) {
            UserModel newUser = new UserModel();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            repository.save(newUser);

            model.addAttribute("message", "Registration completed successfully!");
            return "login";
        }

        model.addAttribute("error", "The user exists !");
        return "register";
    }
}
