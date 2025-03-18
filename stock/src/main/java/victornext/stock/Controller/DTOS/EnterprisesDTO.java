package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record EnterprisesDTO(
        Long id,
        @NotEmpty(message = "The enterprise field cannot be empty.") String name
) {
}
