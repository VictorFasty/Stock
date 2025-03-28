package victornext.stock.Services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;
import victornext.stock.Repositories.Specs.EnterprisesSpecs;
import victornext.stock.validators.EnterprisesValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterprisesService {
    private final EnterprisesRepository repository;
    private final EnterprisesValidator validator;









    public ResponseEntity<EnterprisesModel> Create(EnterprisesModel model) {
        validator.validation(model);
        EnterprisesModel savedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnterprise);
    }




    public ResponseEntity<List<EnterprisesModel>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }



    public ResponseEntity<Object> findById(Long id) {
        validator.validateId(id);
        Optional<EnterprisesModel> enterprise = repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }




    public ResponseEntity<?> delete(Long id) {
        validator.validateId(id);
        ResponseEntity<Object> response = findById(id);
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Enterprise successfully deleted");
    }




    public ResponseEntity<?> update(EnterprisesModel model) {
        validator.validation(model);
        EnterprisesModel updatedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEnterprise);
    }


    public Page<EnterprisesModel> Search(String name, Integer page, Integer pageSize) {
        // validator
        validator.validateSearchName(name);

        Specification<EnterprisesModel> specs = (root, query, cb) -> {
            if (name != null && !name.isEmpty()) {
                return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            }
            return cb.conjunction();
        };

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pageRequest); // Usando findAll com Specification
    }




}
