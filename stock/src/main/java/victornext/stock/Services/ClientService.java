package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.Mappers.ClientMapper;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Model.ClientModel;
import victornext.stock.Repositories.ClientRepository;
import victornext.stock.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final PasswordEncoder encoder;


    public ClientDTO create(ClientDTO dto) {
        if (repository.findByClientId(dto.clientId()) != null) {
            throw new DuplicatedException("Client ID already exists: " + dto.clientId());
        }
        ClientModel model = mapper.toEntity(dto);

        var encryptedPassword = encoder.encode(model.getClientSecret());

        model.setClientSecret(encryptedPassword);

        ClientModel saved = repository.save(model);

        return mapper.toDTO(saved);
    }


    public ClientModel findByClientID(String clientID){
        return repository.findByClientId(clientID);
    }
}
