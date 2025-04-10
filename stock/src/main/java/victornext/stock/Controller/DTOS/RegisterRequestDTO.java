package victornext.stock.Controller.DTOS;

import victornext.stock.Enums.UserRoles;

public record RegisterRequestDTO(
            String name,
            String email,
            String password,
            UserRoles role
) { }
