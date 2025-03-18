package victornext.stock.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import victornext.stock.Model.ProductModel;

import java.util.List;

@Entity
@Table(name = "tb_enterprises", schema = "public")
@Getter
@Setter
@ToString(exclude = "products") // Evita loop infinito no toString()
@EntityListeners(AuditingEntityListener.class)
public class EnterprisesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    @JsonManagedReference // Evita o loop infinito ao serializar a lista de produtos
    private List<ProductModel> products;
}
