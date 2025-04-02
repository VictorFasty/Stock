package victornext.stock.Controller.DTOS;

public record RegisterRequestDTO(
            String name,
            String email,
            String password
) { }
