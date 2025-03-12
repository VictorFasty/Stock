package victornext.stock.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import victornext.stock.Model.EnterprisesModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_products", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "enterprise") // Evita loop infinito no toString()
@EntityListeners(AuditingEntityListener.class)
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false) // Chave estrangeira
    private EnterprisesModel enterprise;

    @org.springframework.data.annotation.CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @org.springframework.data.annotation.LastModifiedDate
    private LocalDateTime updatedAt;
}
