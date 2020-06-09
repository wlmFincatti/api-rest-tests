package br.com.apirest.users.unittest.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import br.com.apirest.users.usecase.EditUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EditUserTest {

    private UserRepository userRepository;
    private EditUser editUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        editUser = new EditUser(userRepository);
    }

    @Test
    public void shouldUserRepositoryEditOnceTimeAndUpdateUser() {
        User userMock = new User(1, 31, "William");
        when(userRepository.existsById(userMock.getId())).thenReturn(true);

        editUser.execute(userMock);

        verify(userRepository, times(1)).existsById(userMock.getId());
        verify(userRepository, times(1)).editUser(
                userMock.getId(),
                userMock.getName(),
                userMock.getAge()
        );
    }

    @Test
    public void shouldNotUserRepositoryEditOnceTimeAndThrowException() {
        User userMock = new User(1, 31, "William");
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> editUser.execute(userMock));

        assertEquals("User not found to update with id "+userMock.getId(), userNotFoundException.getMessage());
        verify(userRepository, times(1)).existsById(userMock.getId());
        verify(userRepository, times(0)).editUser(
                userMock.getId(),
                userMock.getName(),
                userMock.getAge()
        );

    }
}
