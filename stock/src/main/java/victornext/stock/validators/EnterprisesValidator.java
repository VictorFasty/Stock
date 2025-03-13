package victornext.stock.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.InvalidField;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class EnterprisesValidator {
    private final EnterprisesRepository repository;


    public void validation(EnterprisesModel model){
        Exists(model);
    }

    private void Exists(EnterprisesModel model) {
            if(existEnterprise(model)) {
                throw new DuplicatedException("Enterprise already exists with this name!");
            }

            if(NameNull(model)) {
                throw new InvalidField("Field null or not valid !");
            }
    }

    private boolean NameNull(EnterprisesModel model){
        return model.getName() == null;
    }


    private boolean existEnterprise(EnterprisesModel model) {
        if (model.getName() == null) {
            return false; // Nome nulo não deve indicar duplicidade
        }

        Optional<EnterprisesModel> enterpriseFound = repository.findByName(model.getName());

        if (enterpriseFound.isEmpty()) {
            return false; // Se não encontrou nenhuma empresa, retorna falso
        }

        // Se estiver cadastrando uma nova empresa (ID nulo), basta verificar se já existe uma com o mesmo nome
        if (model.getId() == null) {
            return true;
        }

        // Se encontrou uma empresa com o mesmo nome, verifica se o ID é diferente
        return !enterpriseFound.get().getId().equals(model.getId());

    }
}
