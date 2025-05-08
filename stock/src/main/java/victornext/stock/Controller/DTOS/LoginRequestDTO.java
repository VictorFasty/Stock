package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email(message = "Email not valid !!")
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 6, max = 15)
        String password
) {
}
