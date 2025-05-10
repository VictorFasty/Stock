package victornext.stock.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import victornext.stock.Enums.UserRoles;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name= "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;


    public UserModel(String login, String email, String password, UserRoles role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
