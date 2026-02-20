package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterUserResponseDTO(
        @NotBlank
        String name,


        @NotBlank
        @Email
        String email) {
}
