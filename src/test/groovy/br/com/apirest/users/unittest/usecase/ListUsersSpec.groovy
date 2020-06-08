package br.com.apirest.users.unittest.usecase

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.domain.port.UserRepository
import br.com.apirest.users.usecase.ListUsers
import spock.lang.Specification

class ListUsersSpec extends Specification {

    UserRepository userRepository
    ListUsers listUsers

    def setup() {
        userRepository = Mock()
        listUsers = new ListUsers(userRepository)
    }

    def "should find all users"() {
        given:
        def listUsersMock = Arrays.asList(new User(1, 31, "William"), new User(2, 23, "Jessica"))

        when:
        def listUsersResult = listUsers.execute()

        then:
        1 * userRepository.findAll() >> listUsersMock
        assert listUsersResult.size() == 2
        assert listUsersMock == listUsersMock
    }
}
