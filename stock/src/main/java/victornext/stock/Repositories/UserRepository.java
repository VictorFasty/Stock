package victornext.stock.Repositories;


import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import victornext.stock.Model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}
