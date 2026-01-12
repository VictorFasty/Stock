package victornext.stock.Repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import victornext.stock.Model.EnterprisesModel;

import java.util.List;
import java.util.Optional;

public interface EnterprisesRepository extends JpaRepository<EnterprisesModel, Long>, JpaSpecificationExecutor<EnterprisesModel> {
    Optional<EnterprisesModel> findByName(String name);
    Page<EnterprisesModel> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
