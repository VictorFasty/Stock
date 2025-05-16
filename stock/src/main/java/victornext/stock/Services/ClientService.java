package victornext.stock.Services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import victornext.stock.Model.ClientModel;
import victornext.stock.Repositories.ClientRepository;
import victornext.stock.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;


    public ClientModel create (ClientModel model){
        return repository.save(model);
    }


    public ClientModel findByClientID(String clientID){
        return repository.findByClientID(clientID);
    }
}
