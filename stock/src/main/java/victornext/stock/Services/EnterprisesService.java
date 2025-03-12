package victornext.stock.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

import java.util.List;

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

}
