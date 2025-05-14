package victornext.stock.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import victornext.stock.Enums.UserRoles;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String FINAL_PASSWORD = "321";

    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {


        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Optional<UserModel> optionalUser = service.findByEmail(email);

        UserModel user;
        if (optionalUser.isEmpty()) {
            user = createUserGoogle(email);
        } else {
            user = optionalUser.get();
        }


        CustomAuthentication customAuthentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(customAuthentication);


        super.onAuthenticationSuccess(request, response, authentication);
    }

    private UserModel createUserGoogle(String email) {
        UserModel user;
        user = new UserModel();
        user.setEmail(email);
        user.setLogin(FindLoginByEmail(email));
        user.setPassword(FINAL_PASSWORD);
        user.setRole(UserRoles.valueOf("USER"));
        service.create(user);
        return user;
    }

    private String FindLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
