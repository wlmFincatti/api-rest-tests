package br.com.apirest.users.integrationtest.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.entrypoint.CustomExceptionHandler;
import br.com.apirest.users.entrypoint.dto.UserDto;
import br.com.apirest.users.entrypoint.rest.UserController;
import br.com.apirest.users.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;
    private EditUser editUser;
    private DeleteUser deleteUser;
    private ListUsers listUsers;
    private FindUser findUser;
    private CreateUser createUser;
    private UserController userController;
    private User userMock;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = mock(ModelMapper.class);
        editUser = mock(EditUser.class);
        deleteUser = mock(DeleteUser.class);
        listUsers = mock(ListUsers.class);
        findUser = mock(FindUser.class);
        createUser = mock(CreateUser.class);
        userController = new UserController(findUser, createUser, listUsers, deleteUser, editUser, modelMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(CustomExceptionHandler.class).build();
        userMock = new User(1, 31, "William");
        when(modelMapper.map(any(), any())).thenReturn(new UserDto(1, "William", 31));
    }

    @Test
    public void shouldFindUserByIdController() throws Exception {
        when(findUser.execute(1)).thenReturn(userMock);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("William")))
                .andExpect(jsonPath("$.age", is(31)))
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn();

        verify(findUser, times(1)).execute(userMock.getId());
    }

    @Test
    public void shouldNotFoundUserByIdController() throws Exception {
        when(findUser.execute(1)).thenThrow(UserNotFoundException.class);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(findUser, times(1)).execute(userMock.getId());
    }
}
