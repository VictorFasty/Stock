package victornext.stock.Repositories;


import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import victornext.stock.Model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
}
