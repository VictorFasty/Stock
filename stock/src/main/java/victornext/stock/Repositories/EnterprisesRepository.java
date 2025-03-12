package victornext.stock.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import victornext.stock.Model.EnterprisesModel;

public interface EnterprisesRepository extends JpaRepository<EnterprisesModel, Long> {
}
