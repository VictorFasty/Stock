package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotBlank(message = "O e-mail não pode estar em branco")
        String email,

        @NotBlank(message = "A senha é obrigatória para o login manual")
        String password) {
}
