package victornext.stock.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import victornext.stock.Controller.Mappers.UserMapper;
import victornext.stock.Repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private final UserRepository repository;
    @Mock
    private final PasswordEncoder encoder;
    @Mock
    private final UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Nested
    void create() {
        
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findALl() {
    }

    @Test
    void findByLogin() {
    }
}