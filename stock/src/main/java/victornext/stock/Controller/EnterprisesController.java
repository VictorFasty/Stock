package victornext.stock.Controller;


import jakarta.persistence.TableGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Controller.Mappers.EnterprisesMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Services.EnterprisesService;

@RestController
@RequestMapping("Enterprises")
@RequiredArgsConstructor
public class EnterprisesController {
    private EnterprisesMapper mapper;
    private EnterprisesService service;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody @Valid EnterprisesDTO dto) {
        EnterprisesModel model = mapper.toEntity(dto);
        service.Create(model);

        return ResponseEntity.ok(model);
    }
}
