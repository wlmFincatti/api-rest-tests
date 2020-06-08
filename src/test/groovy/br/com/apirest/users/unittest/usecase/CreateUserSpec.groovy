package br.com.apirest.users.unittest.usecase

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.domain.port.UserRepository
import br.com.apirest.users.usecase.CreateUser
import spock.lang.Specification

class CreateUserSpec extends Specification {

    UserRepository userRepository
    CreateUser createUser

    def setup() {
        userRepository = Mock()
        createUser = new CreateUser(userRepository)
    }

    def "should create user"() {
        given:
        User userMock = new User(1, 31, "William")

        when:
        User userResult = createUser.execute(userMock)

        then:
        1 * userRepository.save(_) >> userMock
        with(userResult) {
            assert name == userMock.name
            assert id == userMock.id
            assert age == userMock.age
        }
    }
}
