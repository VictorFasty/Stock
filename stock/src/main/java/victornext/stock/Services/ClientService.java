package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<?> create(ClientDTO dto) {
        ClientModel modelSaved = mapper.toEntity(dto);

        if(repository.existsByClientId(dto.clientId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login Ja Registrado, tente outro");
        }

        repository.save(modelSaved);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public ClientModel findByClientID(String clientID){
        return repository.findByClientId(clientID);
    }
}
