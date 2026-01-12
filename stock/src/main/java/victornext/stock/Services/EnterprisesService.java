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
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;
import victornext.stock.Repositories.Specs.EnterprisesSpecs;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterprisesService {
    private final EnterprisesRepository repository;









    public ResponseEntity<EnterprisesModel> Create(EnterprisesModel model) {
        EnterprisesModel savedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnterprise);
    }




    public ResponseEntity<List<EnterprisesModel>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }



    public ResponseEntity<Object> findById(Long id) {
        if(id == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
        Optional<EnterprisesModel> enterprise = repository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }




    public ResponseEntity<?> delete(Long id) {
        if(id == null) {
            throw new NotFoundException("Product with ID " + id + " not found.");
        }
        ResponseEntity<Object> response = findById(id);
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Enterprise successfully deleted");
    }




    public ResponseEntity<?> update(EnterprisesModel model) {
        if(model.getId() == null) {
            throw new NotFoundException("Product with ID " + model.getId() + " not found.");
        }
        EnterprisesModel updatedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEnterprise);
    }


    public Page<EnterprisesModel> Search(String name, Integer page, Integer pageSize) {
        if (name == null || name.isEmpty()) {
            throw new NotFoundException("The name for search cannot be empty or null.");
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);

        return repository.findByNameContainingIgnoreCase(name, pageRequest);
    }




}
