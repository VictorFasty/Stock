package victornext.stock.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import victornext.stock.Model.ClientModel;

import java.util.UUID;


public interface ClientRepository extends JpaRepository<ClientModel, UUID> {

    ClientModel findByClientId(String clientId);
}
