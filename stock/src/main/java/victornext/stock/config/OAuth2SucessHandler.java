package victornext.stock.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;
import victornext.stock.Model.User;
import victornext.stock.Repositories.UserRepository;
import victornext.stock.Services.TokenService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRepository userRepository;


    private void onAuthenticationSucess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);

            return userRepository.save(newUser);
        });


    String token = tokenService.generatedToken(user);

    String frontendURL = "http://localhost:3000/oauth/redirect?token=" + token;
    getRedirectStrategy().sendRedirect(request, response, frontendURL);


    }
}
