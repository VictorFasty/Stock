package victornext.stock.Repositories.Specs;

import org.springframework.data.jpa.domain.Specification;
import victornext.stock.Model.ProductModel;

public class ProductSpecs {
    public static Specification<ProductModel> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }


}
