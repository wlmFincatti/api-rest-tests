package br.com.apirest.users.unittest.usecase

import br.com.apirest.users.domain.exceptions.UserNotFoundException
import br.com.apirest.users.domain.port.UserRepository
import br.com.apirest.users.usecase.DeleteUser
import spock.lang.Specification
import spock.lang.Unroll

class DeleteUserSpec extends Specification {

    UserRepository userRepository
    DeleteUser deleteUser

    def setup() {
        userRepository = Mock()
        deleteUser = new DeleteUser(userRepository)
    }

    @Unroll
    def "should find user and delete by id #id"() {
        when:
        deleteUser.execute(id)

        then:
        1 * userRepository.existsById(id) >> true
        1 * userRepository.deleteById(id)

        //using data pipe of Spock
        where:
        id << [1, 2, 3]
    }

    def "should not find user by id when try to delete"() {
        given:
        def id = 23

        when:
        deleteUser.execute(id)

        then:
        Throwable exception = thrown()
        verifyAll(exception) {
            exception.class == UserNotFoundException.class
            message == "user not exist to delete with id " + id
        }
        1 * userRepository.existsById(id) >> false
        0 * userRepository.deleteById(id)

    }

}
