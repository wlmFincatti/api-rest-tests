package br.com.apirest.users.entrypoint.rest;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.usecase.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@ApiModel(subTypes = {User.class})
@RestController
@CrossOrigin(origins = "http://localhost:8081")
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

    @ApiOperation(value = "Find user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "status code 200 user found"),
            @ApiResponse(code = 404, message = "status code 200 user not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(findUser.execute(id));
    }

    @ApiOperation(value = "List all users")
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(listUsers.execute());
    }

    @ApiOperation(value = "Create new user")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User userCreated = createUser.execute(user);
        URI uri = UriComponentsBuilder.fromPath("/api/v1/users/{id}").buildAndExpand(userCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(userCreated);
    }

    @ApiOperation(value = "If user exists delete")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer idUser) {
        deleteUser.execute(idUser);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update user")
    @PutMapping
    public ResponseEntity editUser(@Valid @RequestBody User user) {
        editUser.execute(user);
        return ResponseEntity.noContent().build();
    }
}
