package br.com.apirest.users.unittest.usecase;

import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import br.com.apirest.users.usecase.DeleteUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class DeleteUserTest {

    private UserRepository userRepository;
    private DeleteUser deleteUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        deleteUser = new DeleteUser(userRepository);
    }

    @Test
    public void shouldFindUserAndDeleteById() {
        when(userRepository.existsById(anyInt())).thenReturn(true);

        deleteUser.execute(anyInt());

        verify(userRepository, times(1)).existsById(anyInt());
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void shouldNotFinduserByIdWhenTryToDelete() {
        UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> deleteUser.execute(11));
        assertEquals("user not exist to delete with id 11", userNotFoundException.getMessage());
        verify(userRepository, times(1)).existsById(anyInt());
        verify(userRepository, times(0)).deleteById(anyInt());
    }
}
