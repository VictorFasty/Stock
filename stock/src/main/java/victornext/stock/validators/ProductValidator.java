package victornext.stock.validators;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.InvalidField;
import victornext.stock.Model.ProductModel;
import victornext.stock.Repositories.ProductRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductValidator {
    private final ProductRepository repository;

        public void ValidatorALL(ProductModel model) {
            Exists(model);
        }

    private void Exists(ProductModel model) {
                if(existsProduct(model)){
                    throw new DuplicatedException("Enterprise already exists with this name!");
                }

                if(OtherNull(model)) {
                    throw new InvalidField("Field null or not valid !");
                }
    }

    private boolean OtherNull(ProductModel model) {
        return model.getName() == null;
    }


    private boolean existsProduct(ProductModel model) {
        if(model.getName() == null) {
            return false;
        }

        Optional<ProductModel> ProductFound = repository.findByName(model.getName());
;
        if(ProductFound.isEmpty()) {
            return true;
        }

        return !ProductFound.get().getId().equals(model.getId());

    }
}
