package victornext.stock.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class EnterprisesValidator {
    private final EnterprisesRepository repository;


    public void validation(EnterprisesModel model){
        duplicated(model);
        Exists(model);
    }

    private void Exists(EnterprisesModel model) {
        
    }


    private boolean existEnterprise(EnterprisesModel model) {
        Optional<EnterprisesModel> enterpriseFound = repository.findByName(model.getName());

        // Verifica se o ID do modelo é nulo
        if (model.getId() == null) {
            return enterpriseFound.isPresent();
        }

        return enterpriseFound
                .map(EnterprisesModel::getId)
                .filter(id -> !id.equals(model.getId()))
                .isPresent();
    }

}
