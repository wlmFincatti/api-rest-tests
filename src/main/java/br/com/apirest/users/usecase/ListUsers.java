package br.com.apirest.users.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUsers {

    private UserRepository userRepository;

    @Autowired
    public ListUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }

}
