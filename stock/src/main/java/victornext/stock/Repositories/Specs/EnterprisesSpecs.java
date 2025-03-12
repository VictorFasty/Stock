package victornext.stock.Repositories.Specs;

import org.springframework.data.jpa.domain.Specification;
import victornext.stock.Model.EnterprisesModel;

public class EnterprisesSpecs {
    public static Specification<EnterprisesModel> nameLike(String name) {
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
    }
}

