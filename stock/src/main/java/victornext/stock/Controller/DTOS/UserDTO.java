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

        UserRoles roles
) {
    public UserDTO(Long id, String name, String email, String password) {
        this(id, name, email, password, UserRoles.USER);
    }
}
