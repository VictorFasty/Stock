package victornext.stock.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Controller.Mappers.EnterprisesMapper;
import victornext.stock.Exceptions.InvalidField;
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Repositories.EnterprisesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterprisesService {
    private final EnterprisesRepository repository;
    private final EnterprisesMapper mapper;







    public ResponseEntity<EnterprisesModel> create(EnterprisesDTO dto) {
        EnterprisesModel savedModel = mapper.toEntity(dto);
        repository.save(savedModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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




    public EnterprisesDTO update(Long id, EnterprisesDTO dto) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Enterprise not found with ID: " + id);
        }


        EnterprisesModel model = mapper.toEntity(dto);

        model.setId(id);

        EnterprisesModel saved = repository.save(model);
        return mapper.toDTO(saved);
    }


    public Page<EnterprisesDTO> search(String name, Integer page, Integer pageSize) {
        if (name == null || name.isBlank()) {
            throw new InvalidField("The name for search cannot be empty or null.");
        }

        Pageable pageRequest = PageRequest.of(page, pageSize);


        Page<EnterprisesModel> resultado = repository.findByNameContainingIgnoreCase(name, pageRequest);

        return resultado.map(mapper::toDTO);
    }




}
