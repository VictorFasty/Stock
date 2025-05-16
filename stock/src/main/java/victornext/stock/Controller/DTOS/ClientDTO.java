package victornext.stock.Controller.DTOS;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ClientDTO(

        @NotNull(message = "The id field cannot be null.")
        UUID id,

        @NotEmpty(message = "The clientId field cannot be empty.")
        String clientId,

        @NotEmpty(message = "The clientSecret field cannot be empty.")
        String clientSecret,

        @NotEmpty(message = "The redirectURI field cannot be empty.")
        String redirectURI,

        String scope

) {}