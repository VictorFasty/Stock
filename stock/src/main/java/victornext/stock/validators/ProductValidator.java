package victornext.stock.validators;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.InvalidField;
import victornext.stock.Exceptions.NotFoundException;
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

        public void validateId(Long id) {
            if (isIdInvalid(id)) {
                throw new NotFoundException("Product with ID " + id + " not found.");
            }
        }



        public void validateSearchName(String name) {
            if (name == null || name.isEmpty()) {
                throw new NotFoundException("O nome para pesquisa não pode ser vazio ou nulo.");
            }
        }



         public boolean isIdInvalid(Long id) {
            return repository.findById(id).isEmpty();
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
