package victornext.stock.Controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import victornext.stock.Security.CustomAuthentication;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String LoginPage(){
        return "login";
    }


    @GetMapping("/")
    public String homePage(Authentication authentication) {
        if(authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUserModel());
        }
        return "Hello " + authentication.getName();
    }
}
