package victornext.stock.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class ClientModel {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "client_id")
    private String clientId;

    private String clientSecret;

    private String redirectURI;

    private String scope;
}
