package br.com.apirest.users.unittest.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.entrypoint.dto.UserDto;
import br.com.apirest.users.entrypoint.rest.UserController;
import br.com.apirest.users.usecase.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private FindUser findUser;
    private CreateUser createUser;
    private ListUsers listUsers;
    private DeleteUser deleteUser;
    private EditUser editUser;
    private UserController userController;

    @BeforeEach
    void setUp() {
        findUser = mock(FindUser.class);
        createUser = mock(CreateUser.class);
        listUsers = mock(ListUsers.class);
        deleteUser = mock(DeleteUser.class);
        editUser = mock(EditUser.class);
        userController = new UserController(findUser, createUser, listUsers, deleteUser, editUser);
    }

    @AfterEach
    void tearDown() {
        findUser = null;
        createUser = null;
        listUsers = null;
        deleteUser = null;
        editUser = null;
        userController = null;
    }

    @Test
    @DisplayName("should call method execute of findUser once time and return user")
    void findUserById() {
        UserDto userMock = new UserDto("william", 31);
        when(findUser.execute(Mockito.anyInt())).thenReturn(new User(1, userMock.getAge(), userMock.getName()));

        ResponseEntity<UserDto> responseEntityResult = userController.findUserById(1);

        verify(findUser, times(1)).execute(1);
        assertEquals(HttpStatus.OK, responseEntityResult.getStatusCode());
        assertEquals(userMock.getAge(), responseEntityResult.getBody().getAge());
        assertEquals(userMock.getName(), responseEntityResult.getBody().getName());
    }

    @Test
    @DisplayName("should call method execute of deleteUser once time")
    void listUsers() {
        List<User> listUserMock = Arrays.asList(new User(1, 31, "William"), new User(2, 23, "Ana"));
        when(listUsers.execute()).thenReturn(listUserMock);

        ResponseEntity<List<UserDto>> listResponseEntity = userController.listUsers();

        verify(listUsers, times(1)).execute();
        assertThat(listResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertEquals(listResponseEntity.getBody().size(), 2);
    }

    @Test
    @DisplayName("should call method execute of createUser once time and retun a user")
    void createUserController() {
        User userMock = new User(1, 31, "William");
        when(createUser.execute(userMock)).thenReturn(userMock);

        ResponseEntity<UserDto> userResponse = userController.createUser(userMock);

        verify(createUser, times(1)).execute(userMock);
        assertThat(userResponse.getStatusCode(), is(HttpStatus.CREATED));
        assertTrue(userResponse.getBody().getName().equals(userMock.getName()));
        assertTrue(userResponse.getBody().getAge().equals(userMock.getAge()));
    }

    @Test
    @DisplayName("should call method execute of deleteUser once time")
    void deleteUser() {
        ResponseEntity response = userController.deleteUser(1);

        verify(deleteUser, times(1)).execute(1);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertFalse(response.hasBody());
    }

    @Test
    @DisplayName("should call method execute of editUser once time")
    void editUser() {
        User userMock = new User(1, 31, "William");

        ResponseEntity response = userController.editUser(userMock);

        verify(editUser, times(1)).execute(userMock);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertFalse(response.hasBody());
    }
}