package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductDTO (
        Long id,

        @NotEmpty(message = "The name field cannot be empty.")
        String name,

        @NotNull(message = "The quantity field cannot be null.")
        Integer quantity,

        @NotNull(message = "The price field cannot be null.")
        Double price,

        @NotNull(message = "The enterprise field canot be null")
        Long idEnterprise
) {}

