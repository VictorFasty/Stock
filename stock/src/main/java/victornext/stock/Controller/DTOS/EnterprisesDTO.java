package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotBlank;

public record EnterprisesDTO(
        Long id,
        @NotBlank(message = "nome da empresa não pode estar vazio") String name
) {
}
