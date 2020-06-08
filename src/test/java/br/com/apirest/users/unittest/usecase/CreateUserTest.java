package br.com.apirest.users.unittest.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.port.UserRepository;
import br.com.apirest.users.usecase.CreateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class CreateUserTest {

    private UserRepository userRepository;
    private CreateUser createUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        createUser = new CreateUser(userRepository);
    }

    @Test
    @DisplayName("should user repository save once time and create one user")
    public void shouldUserRepositorySave() {
        User user = new User(1, 31, "william");
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User userSaved = createUser.execute(user);

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        assertThat(user.getId(), is(userSaved.getId()));
        assertThat(user.getName(), is(userSaved.getName()));
    }


}