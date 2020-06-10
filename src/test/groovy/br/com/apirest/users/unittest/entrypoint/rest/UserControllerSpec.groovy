package br.com.apirest.users.unittest.entrypoint.rest

import br.com.apirest.users.domain.entity.User

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
    User userMock;

    def setup() {
        editUser = Mock()
        createUser = Mock()
        deleteUser = Mock()
        listUsers = Mock()
        findUser = Mock()
        userController = new UserController(findUser, createUser, listUsers, deleteUser, editUser)
        userMock = new User(1, 31, "William")
    }

    def cleanup() {
        editUser = null
        createUser = null
        deleteUser = null
        listUsers = null
        findUser = null
        userController = null
        userMock = null
    }

    def "should call method execute of findUser once time and return user"() {
        when:
        ResponseEntity<User> userResponse = userController.findUserById(userMock.getId())

        then:
        1 * findUser.execute(_) >> userMock
        with(userResponse) {
            getStatusCode() == HttpStatus.OK
            getBody().getName() == userMock.getName()
            getBody().getAge() == userMock.getAge()
            getBody().getId() == userMock.getId()
        }
    }

    def "should call method execute of deleteUser once time"() {
        when:
        ResponseEntity<User> response = userController.deleteUser(userMock.getId())

        then:
        1 * deleteUser.execute(_)
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "should call method execute of editUser once time"() {
        when:
        ResponseEntity<User> response = userController.editUser(userMock)

        then:
        1 * editUser.execute(_) >> {
            User u ->
                assert u.getName() == "William"
                assert u.getAge() == 31
                assert u.getId() == 1
        }
        response.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "should call method execute of listUsers once time and retun a list of users"() {
        given:
        List<User> listUsersMock = Arrays.asList(userMock, userMock);

        when:
        ResponseEntity<List<User>> response = userController.listUsers()

        then:
        1 * listUsers.execute() >> listUsersMock
        and:
        verifyAll(response) {
            getStatusCode() == HttpStatus.OK
            getBody().size() == 2
            getBody().eachWithIndex { user, idx ->
                with(user) {
                    getName() == listUsersMock[idx].getName()
                    getAge() == listUsersMock[idx].getAge()
                }
            }
        }
    }

    def "should call method execute of createUser once time and retun a user"() {
        when:
        ResponseEntity<User> responseUser = userController.createUser(userMock)

        then:
        1 * createUser.execute(_) >> userMock
        and:
        verifyAll(responseUser) {
            getStatusCode() == HttpStatus.CREATED
            getBody().getName() == userMock.getName()
            getBody().getAge() == userMock.getAge()
            getBody().getId() == userMock.getId()
            headers.getLocation().toString() == "/api/v1/users/1"
        }
    }

}
