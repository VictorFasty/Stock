package victornext.stock.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProviderr implements AuthenticationProvider {
    private final UserService service;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String login = authentication.getName();
       String password = authentication.getCredentials().toString();

        UserModel userFind = service.FindByLogin(login);

        if(userFind == null) {
            throw getUserNotFounded();
        }

        String passwordEncrypted = userFind.getPassword();

        boolean passwordOK = encoder.matches(password, passwordEncrypted);

        if(passwordOK){
            return new CustomAuthentication(userFind);
        }

        throw getUserNotFounded();
    }

    private UsernameNotFoundException getUserNotFounded() {
        return new UsernameNotFoundException("Wrong username or password!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
