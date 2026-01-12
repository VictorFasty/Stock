package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.Mappers.ClientMapper;
import victornext.stock.Model.ClientModel;
import victornext.stock.Repositories.ClientRepository;
import victornext.stock.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final PasswordEncoder encoder;


    public ClientModel create (ClientDTO dto){
        ClientModel model = mapper.toEntity(dto);
        var encryptedPassword = encoder.encode(model.getClientSecret());
        model.setClientSecret(encryptedPassword);
        return repository.save(model);

    }


    public ClientModel findByClientID(String clientID){
        return repository.findByClientId(clientID);
    }
}
