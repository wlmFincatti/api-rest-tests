package br.com.apirest.users.unittest.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import br.com.apirest.users.usecase.FindUser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FinUserTest {

    private UserRepository userRepository;
    private FindUser findUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        findUser = new FindUser(userRepository);
    }

    @ParameterizedTest
    @CsvSource({"1, William", "2, Maria"})
    public void shouldFindUserById(Integer id, String name) {
        User userMock = new User(id, 31, name);
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));

        User userResult = findUser.execute(userMock.getId());

        verify(userRepository, times(1)).findById(userMock.getId());
        assertThat(userResult, Matchers.is(userMock));
    }

    @Test
    public void shouldNotFindUserById() {
        Integer id = 32;

        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
                () -> findUser.execute(id));

        assertEquals("User not found with id " + id, userNotFoundException.getMessage());
        verify(userRepository, times(1)).findById(id);
    }

}
