package victornext.stock.Controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String LoginPage(){
        return "login";
    }


    @GetMapping("/")
    public String homePage(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
}
