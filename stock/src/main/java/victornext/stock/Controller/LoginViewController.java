package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import victornext.stock.Security.CustomAuthentication;

@Controller
@Tag(name = "Login View", description = "Endpoints for view-based login and homepage")
public class LoginViewController {

    @Operation(summary = "Login page", description = "Returns the login view (HTML page)")
    @GetMapping("/login")
    public String LoginPage() {
        return "login"; // returns login.html from templates
    }

    @Operation(summary = "Home page", description = "Returns a welcome message for the logged-in user")
    @GetMapping("/")
    @ResponseBody // This allows Swagger to interpret the return as a response body
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUserModel());
        }
        return "Hello " + authentication.getName();
    }
}
