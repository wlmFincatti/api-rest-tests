package br.com.apirest.users.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.entrypoint.converter.UserConverter;
import br.com.apirest.users.entrypoint.dto.UserDto;
import br.com.apirest.users.usecase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private FindUser findUser;
    public CreateUser createUser;
    private ListUsers listUsers;
    private DeleteUser deleteUser;
    private EditUser editUser;

    @Autowired
    public UserController(FindUser findUser, CreateUser createUser, ListUsers listUsers, DeleteUser deleteUser, EditUser editUser) {
        this.findUser = findUser;
        this.createUser = createUser;
        this.listUsers = listUsers;
        this.deleteUser = deleteUser;
        this.editUser = editUser;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(UserConverter.convertDto(findUser.execute(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(UserConverter.convertDtos(listUsers.execute()));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {
        UserDto userDto = UserConverter.convertDto(createUser.execute(user));
        URI uri = UriComponentsBuilder.fromPath("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer idUser) {
        deleteUser.execute(idUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity editUser(@Valid @RequestBody User user) {
        editUser.execute(user);
        return ResponseEntity.noContent().build();
    }
}
