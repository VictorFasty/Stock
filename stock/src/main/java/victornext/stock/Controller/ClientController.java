package victornext.stock.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import victornext.stock.Controller.DTOS.ClientDTO;
import victornext.stock.Controller.Mappers.ClientMapper;
import victornext.stock.Model.ClientModel;
import victornext.stock.Services.ClientService;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Endpoints for managing registered OAuth2 clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;
    private final PasswordEncoder encoder;





    // ---------------------- CREATE ----------------------
    @Operation(summary = "Create a new client", description = "Registers a new OAuth2 client with encoded secret")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid client data"),
            @ApiResponse(responseCode = "403", description = "Access denied - only ADMINs can perform this operation")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ClientModel create(@RequestBody ClientDTO dto) {
        ClientModel model = mapper.toEntity(dto);
        var encryptedPassword = encoder.encode(model.getClientSecret());
        model.setClientSecret(encryptedPassword);
        return clientService.create(model);
    }





    
    // ---------------------- AUTHORIZATION CODE CALLBACK ----------------------
    @Operation(summary = "Receive OAuth2 authorization code", description = "Endpoint to receive the authorization code from OAuth2 flow")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authorization code received successfully")
    })
    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Your authorization code: " + code;
    }
}
