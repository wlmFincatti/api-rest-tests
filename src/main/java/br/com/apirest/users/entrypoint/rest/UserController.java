package br.com.apirest.users.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.entrypoint.dto.UserDto;
import br.com.apirest.users.usecase.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(subTypes = {User.class})
@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/v1/users")
public class UserController {

    private FindUser findUser;
    private CreateUser createUser;
    private ListUsers listUsers;
    private DeleteUser deleteUser;
    private EditUser editUser;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(FindUser findUser, CreateUser createUser, ListUsers listUsers, DeleteUser deleteUser, EditUser editUser, ModelMapper modelMapper) {
        this.findUser = findUser;
        this.createUser = createUser;
        this.listUsers = listUsers;
        this.deleteUser = deleteUser;
        this.editUser = editUser;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "status code 200 user found"),
            @ApiResponse(code = 404, message = "status code 200 user not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.convertToDto(findUser.execute(id)));
    }

    @ApiOperation(value = "List all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> listAllUsers() {
        return ResponseEntity.ok(this.convertToDtos(listUsers.execute()));
    }

    @ApiOperation(value = "Create new user")
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody User user) {
        User userCreated = createUser.execute(user);
        URI uri = UriComponentsBuilder.fromPath("/api/v1/users/{id}").buildAndExpand(userCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(this.convertToDto(userCreated));
    }

    @ApiOperation(value = "If user exists delete")
    @DeleteMapping("/{id}")
    public ResponseEntity removeUser(@PathVariable("id") Integer idUser) {
        deleteUser.execute(idUser);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update user")
    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody User user) {
        editUser.execute(user);
        return ResponseEntity.noContent().build();
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private List<UserDto> convertToDtos(List<User> users) {
        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
