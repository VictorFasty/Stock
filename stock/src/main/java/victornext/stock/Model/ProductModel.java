package victornext.stock.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import victornext.stock.Model.EnterprisesModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_products", schema = "public")
@Data
@ToString(exclude = "enterprise") // Evita loop infinito no toString()
@EntityListeners(AuditingEntityListener.class)
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer quantity;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tb_enterprise", nullable = false) // Coluna correta no banco de dados
    @JsonBackReference // Evita o loop infinito ao serializar a referência ao enterprise
    private EnterprisesModel enterprise;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
