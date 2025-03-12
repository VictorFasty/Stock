package victornext.stock.Services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

@Service
@RequiredArgsConstructor
public class EnterprisesService {
    private final EnterprisesRepository repository;



    public ResponseEntity<EnterprisesModel> Create(EnterprisesModel model) {
        EnterprisesModel savedEnterprise = repository.save(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnterprise);
    }



}
