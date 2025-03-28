package victornext.stock.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.EnterprisesDTO;
import victornext.stock.Controller.Mappers.EnterprisesMapper;
import victornext.stock.Model.EnterprisesModel;
import victornext.stock.Services.EnterprisesService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Enterprises")
@RequiredArgsConstructor
public class EnterprisesController {

    private final EnterprisesMapper mapper;
    private final EnterprisesService service;







    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody @Valid EnterprisesDTO dto) {
        EnterprisesModel model = mapper.toEntity(dto);
        service.Create(model);

        return ResponseEntity.ok(model);
    }










    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody @Valid EnterprisesDTO dto) {
        EnterprisesModel model = mapper.toEntity(dto);
        model.setId(id);

        return service.update(model);
    }









    @GetMapping(value = "/findall")
    public ResponseEntity<List<EnterprisesModel>> findall() {
        return service.findAll();
    }








    @GetMapping(value = "/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }






    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable (value = "id") Long id) {
        return service.delete(id);
    }



    @GetMapping("/search/{name}")
    public ResponseEntity<Page<EnterprisesDTO>> search(
            @PathVariable("name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "name,asc") String sort
    )
    {
        Page<EnterprisesModel> resultado = service.Search(name, page, pageSize);
        Page<EnterprisesDTO> lista = resultado.map(mapper::toDTO);
        return ResponseEntity.ok(lista);
    }


}
