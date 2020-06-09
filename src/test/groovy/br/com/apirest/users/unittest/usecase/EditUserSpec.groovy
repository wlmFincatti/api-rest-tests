package br.com.apirest.users.unittest.usecase

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.domain.exceptions.UserNotFoundException
import br.com.apirest.users.domain.port.UserRepository
import br.com.apirest.users.usecase.EditUser
import spock.lang.Narrative
import spock.lang.Specification

@Narrative("Test with usecase edit user")
class EditUserSpec extends Specification {

    UserRepository userRepository;
    EditUser editUser;

    def setup() {
        userRepository = Mock()
        editUser = new EditUser(userRepository)
    }

    def cleanup() {
        userRepository = null
        editUser = null
    }

    def "should user repository edit once time and update user"() {
        given:
        def id = 1
        def userMock = new User(id, 31, "William")

        when:
        editUser.execute(userMock)

        then:
        1 * userRepository.existsById(id) >> true
        1 * userRepository.editUser(_, _, _)
    }

    def "should not user repository edit once time and throw expection"() {
        given:
        def id = 32
        def userMock = new User(id, 41, "Roberto de Souza")

        when:
        editUser.execute(userMock)

        then:
        Throwable exception = thrown()
        verifyAll {
            exception.class == UserNotFoundException.class
            exception.message == "User not found to update with id " + id.toString()
        }
        and:
        0 * editUser.execute(_)
        1 * userRepository.existsById(id)
    }
}
