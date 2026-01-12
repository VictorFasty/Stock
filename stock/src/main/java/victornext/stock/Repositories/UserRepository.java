package victornext.stock.Repositories;


import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import victornext.stock.Model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    UserModel findByLogin(String login);
    boolean existsByLoginAndEmail(String login, String email);
}
