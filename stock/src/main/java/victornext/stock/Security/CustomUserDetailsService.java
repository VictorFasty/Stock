package victornext.stock.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService service;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserModel userModel = service.FindByLogin(login);

        if(userModel == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return User.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .roles(userModel.getRole().name())
                .build();
    }
}
