package victornext.stock.Security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import victornext.stock.Model.UserModel;
import victornext.stock.Services.UserService;

@Component
@AllArgsConstructor
public class SecurityService {
    private final UserService userService;


    public UserModel userLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuthentication){
            return customAuthentication.getUserModel();
        }

        return null;
    }

}
