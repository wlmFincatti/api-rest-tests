package br.com.apirest.users.unittest.usecase

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.domain.exceptions.UserNotFoundException
import br.com.apirest.users.domain.port.UserRepository
import br.com.apirest.users.usecase.FindUser
import spock.lang.Specification
import spock.lang.Unroll

class FindUserSpec extends Specification {

    FindUser findUser;
    UserRepository userRepository;

    def setup() {
        userRepository = Mock()
        findUser = new FindUser(userRepository)
    }

    def cleanup() {
        userRepository = null
        findUser = null
    }

    @Unroll
    def "should find user by id #id"(Integer id, Integer age, String name) {
        given:
        def userMock = new User(id, age, name)

        when:
        User userResult = findUser.execute(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(userMock)
        verifyAll(userResult) {
            getId() == id
            getAge() == age
            getName() == name
        }

        //using data table of Spock
        where:
        id | age | name
        1  | 31  | "William"
        2  | 40  | "Graziela"
    }

    def "should not find user by id"() {
        given:
        def id = 2

        when:
        findUser.execute(id)

        then:
        Throwable excpetion = thrown()
        verifyAll(excpetion) {
            excpetion.class == UserNotFoundException.class
            message == "User not found with id " + id
        }
        1 * userRepository.findById(_) >> Optional.ofNullable()
    }

}
