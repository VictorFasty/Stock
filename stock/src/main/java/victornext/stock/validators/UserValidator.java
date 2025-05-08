package victornext.stock.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import victornext.stock.Exceptions.DuplicatedException;
import victornext.stock.Exceptions.InvalidField;
import victornext.stock.Exceptions.NotFoundException;
import victornext.stock.Model.UserModel;
import victornext.stock.Repositories.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidator {
    private final UserRepository repository;

    public void validateCreate(UserModel model) {
        validateFields(model);
        validateEmailForCreate(model.getEmail(), model.getId());
    }

    public void validateUpdate(UserModel model) {
        validateId(model.getId());
        validateFields(model);
        validateEmailForCreate(model.getEmail(), model.getId());
    }

    public void validateDelete(Long id) {
        validateId(id);
    }

    public void validateFindById(Long id) {
        validateId(id);
    }

    public void validateEmailForSearch(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidField("Email cannot be null or empty.");
        }
    }

    private void validateEmailForCreate(String email, Long id) {
        if (email == null || email.isEmpty()) {
            throw new InvalidField("Email cannot be null or empty.");
        }

        Optional<UserModel> existingUser = repository.findByEmail(email);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new DuplicatedException("Email is already registered.");
        }
    }

    private void validateFields(UserModel model) {
        if (model.getLogin() == null || model.getEmail() == null || model.getPassword() == null) {
            throw new InvalidField("Name, email or password cannot be null.");
        }
    }

    private void validateId(Long id) {
        if (id == null || repository.findById(id).isEmpty()) {
            throw new NotFoundException("User with ID " + id + " not found.");
        }
    }
}
