package victornext.stock.Services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnterprisesService {
    private final EnterprisesRepository repository;


    @Autowired
    public EnterprisesService(EnterprisesRepository repository) {
        this.repository = repository;
    }




    public ResponseEntity<EnterprisesModel> Create(EnterprisesModel model) {
        EnterprisesModel savedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnterprise);
    }




    public ResponseEntity<List<EnterprisesModel>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }



    public ResponseEntity<Object> findById(Long id) {
        Optional<EnterprisesModel> enterprise = repository.findById(id);
        if(enterprise.isEmpty()){
            System.out.println("Not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
    }


    public ResponseEntity<?> delete(Long id) {
        ResponseEntity<Object> response = findById(id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enterprise not found for deletion");
        }

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Enterprise successfully deleted");
    }

    public ResponseEntity<?> update(EnterprisesModel model) {
        if (model.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must not be null");
        }
        
        Optional<EnterprisesModel> existingEnterprise = repository.findById(model.getId());

        if (existingEnterprise.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enterprise not found for update");
        }

        EnterprisesModel updatedEnterprise = repository.save(model);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEnterprise);
    }

}
