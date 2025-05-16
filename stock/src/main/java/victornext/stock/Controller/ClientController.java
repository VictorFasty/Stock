package victornext.stock.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.Mappers.ClientMapper;
import victornext.stock.Model.ClientModel;
import victornext.stock.Services.ClientService;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;
    private final PasswordEncoder encoder;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ClientModel create(@RequestBody ClientDTO dto){
        ClientModel model = mapper.toEntity(dto);
        var PasswordEncrypt = encoder.encode(model.getClientSecret());
        model.setClientSecret(PasswordEncrypt);
        return clientService.create(model);
    }
}
