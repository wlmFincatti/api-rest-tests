package br.com.apirest.users.unittest.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.port.UserRepository;
import br.com.apirest.users.usecase.ListUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListUsersTest {

    private UserRepository userRepository;
    private ListUsers listUsers;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        listUsers = new ListUsers(userRepository);
    }

    @Test
    @DisplayName("should user repository findall once time and return a list of users")
    public void shouldUserRepositoryFindAll() {
        List<User> users = Arrays.asList(new User(1, 31, "william"), new User(2, 119, "robert"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> listUsersResult = listUsers.execute();

        verify(userRepository, times(1)).findAll();
        assertThat(users, is(listUsersResult));
        assertEquals(2, listUsersResult.size());
    }

}