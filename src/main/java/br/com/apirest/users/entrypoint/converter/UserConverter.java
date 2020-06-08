package br.com.apirest.users.entrypoint.converter;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.entrypoint.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserDto convertDto(User user) {
        return UserDto
                .builder()
                .age(user.getAge())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> convertDtos(List<User> users) {
        return users
                .stream()
                .map(user ->
                        UserDto.builder()
                                .name(user.getName())
                                .age(user.getAge())
                                .build()
                ).collect(Collectors.toList());
    }
}
