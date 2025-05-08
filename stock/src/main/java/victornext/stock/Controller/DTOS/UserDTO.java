package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import victornext.stock.Enums.UserRoles;

public record UserDTO(
        Long id,

        @NotBlank(message = "Name is null!!")
        String login,

        @Email(message = "Email not valid!!")
        @NotBlank(message = "Email not null!!")
        String email,

        @NotBlank(message = "Password not blank!!")
        @Size(min = 6, max = 15)
        String password,

        UserRoles role
) {
    public UserDTO(Long id, String login, String email, String password, UserRoles role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : UserRoles.USER;  // Definindo valor padrão
    }
}


