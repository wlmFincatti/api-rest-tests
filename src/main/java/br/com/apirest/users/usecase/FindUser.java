package br.com.apirest.users.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindUser {

    private UserRepository userRepository;

    @Autowired
    public FindUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(Integer idUser) {
        return userRepository
                .findById(idUser).orElseThrow(() -> new UserNotFoundException("User not found with id " + idUser));
    }
}
