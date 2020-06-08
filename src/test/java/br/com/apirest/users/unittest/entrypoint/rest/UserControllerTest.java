package br.com.apirest.users.unittest.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.entrypoint.dto.UserDto;
import br.com.apirest.users.entrypoint.rest.UserController;
import br.com.apirest.users.usecase.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void findUserById() {
        UserDto userMock = new UserDto("william", 31);
        when(findUser.execute(Mockito.anyInt())).thenReturn(new User(1, userMock.getAge(), userMock.getName()));

        ResponseEntity<UserDto> responseEntityResult = userController.findUserById(1);

        verify(findUser, times(1)).execute(1);
        Assert.assertEquals(HttpStatus.OK, responseEntityResult.getStatusCode());
        Assert.assertEquals(userMock.getAge(), responseEntityResult.getBody().getAge());
        Assert.assertEquals(userMock.getName(), responseEntityResult.getBody().getName());
    }

    @Test
    void listUsers() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void editUser() {
    }
}