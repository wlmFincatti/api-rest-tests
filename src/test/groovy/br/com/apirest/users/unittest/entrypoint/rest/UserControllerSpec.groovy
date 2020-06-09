package br.com.apirest.users.unittest.entrypoint.rest

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.entrypoint.dto.UserDto
import br.com.apirest.users.entrypoint.rest.UserController
import br.com.apirest.users.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Narrative
import spock.lang.Specification

@Narrative("Tests CRUD controller")
class UserControllerSpec extends Specification {

    EditUser editUser
    CreateUser createUser
    DeleteUser deleteUser
    ListUsers listUsers
    FindUser findUser
    UserController userController

    def setup() {
        editUser = Mock()
        createUser = Mock()
        deleteUser = Mock()
        listUsers = Mock()
        findUser = Mock()
        userController = new UserController(findUser, createUser, listUsers, deleteUser, editUser)
    }

    def cleanup() {
        editUser = null
        createUser = null
        deleteUser = null
        listUsers = null
        findUser = null
        userController = null
    }

    def "should call method execute of findUser once time and return user"() {
        given:
        def userMock = new User(1, 31, "william")

        when:
        ResponseEntity<UserDto> userResponse = userController.findUserById(1)

        then:
        1 * findUser.execute(_) >> userMock
        with(userResponse) {
            getStatusCode() == HttpStatus.OK
            body.name == userMock.name
            body.age == userMock.age
        }
    }

    def "should call method execute of deleteUser once time"() {
        when:
        ResponseEntity<UserDto> response = userController.deleteUser(1)

        then:
        1 * deleteUser.execute(_)
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "should call method execute of editUser once time"() {
        given:
        User user = new User(1, 31, "William")

        when:
        ResponseEntity<UserDto> response = userController.editUser(user)

        then:
        1 * editUser.execute(_) >> {
            User u ->
                assert u.name == "William"
                assert u.age == 31
                assert u.id == 1
        }
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "should call method execute of listUsers once time and retun a list of users"() {
        given:
        List<User> listUsersMock = Arrays.asList(new User(1, 31, "William"), new User(2, 43, "Ana"))

        when:
        ResponseEntity<List<UserDto>> response = userController.listUsers()

        then:
        1 * listUsers.execute() >> listUsersMock
        and:
        verifyAll(response) {
            getStatusCode() == HttpStatus.OK
            body.size() == 2
            body.eachWithIndex { userDto, idx ->
                with(userDto) {
                    name == listUsersMock[idx].name
                    age == listUsersMock[idx].age
                }
            }
        }
    }

    def "should call method execute of createUser once time and retun a user"() {
        given:
        User userMock = new User(1, 31, "William")

        when:
        ResponseEntity<UserDto> responseUser = userController.createUser(userMock)

        then:
        1 * createUser.execute(_) >> userMock
        and:
        verifyAll(responseUser) {
            getStatusCode() == HttpStatus.CREATED
            body.name == "William"
            body.age == 31
            headers.getLocation().toString() == "/api/v1/users/1"
        }
    }

}
