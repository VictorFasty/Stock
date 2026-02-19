package victornext.stock.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Table(name = "users")
@Entity
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

}
