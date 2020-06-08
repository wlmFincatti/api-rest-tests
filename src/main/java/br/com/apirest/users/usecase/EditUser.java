package br.com.apirest.users.usecase;

import br.com.apirest.users.domain.entity.User;
import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditUser {

    private UserRepository userRepository;

    @Autowired
    public EditUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User user) {

        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException("User not found to update with id " + user.getId().toString());
        }
        userRepository.editProduct(user.getId(), user.getName(), user.getAge());
    }
}
