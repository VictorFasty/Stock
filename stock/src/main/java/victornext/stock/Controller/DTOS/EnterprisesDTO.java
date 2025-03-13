package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotBlank;

public record EnterprisesDTO(
        Long id,
        @NotBlank(message = "The enterprise field cannot be empty.") String name
) {
}
