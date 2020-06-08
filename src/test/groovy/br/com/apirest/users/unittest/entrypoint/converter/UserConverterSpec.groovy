package br.com.apirest.users.unittest.entrypoint.converter

import br.com.apirest.users.domain.entity.User
import br.com.apirest.users.entrypoint.converter.UserConverter
import spock.lang.Specification

class UserConverterSpec extends Specification {

    def "should convert User to UserDto"() {
        when:
        def dtoResult = UserConverter.convertDto(new User(1, 31, "William"))

        then:
        verifyAll(dtoResult) {
            name == "William"
            age == 31
        }
    }

    def "should convert list of User to list UserDto"() {
        given:
        def listUsersMock = Arrays.asList(new User(2, 32, "Ana"), new User(1, 31, "William"))

        when:
        def dtosResult = UserConverter.convertDtos(listUsersMock)

        then:
        dtosResult.eachWithIndex { userDto, idx ->
            with(userDto) {
                assert age == listUsersMock[idx].age
                assert name == listUsersMock[idx].name
            }
        }

    }

}
