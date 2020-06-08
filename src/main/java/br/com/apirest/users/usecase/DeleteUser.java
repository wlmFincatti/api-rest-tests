package br.com.apirest.users.usecase;

import br.com.apirest.users.domain.exceptions.UserNotFoundException;
import br.com.apirest.users.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUser {

    private UserRepository userRepository;

    @Autowired
    public DeleteUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("user not exist to delete with id " + id.toString());
        }

        userRepository.deleteById(id);
    }

}
