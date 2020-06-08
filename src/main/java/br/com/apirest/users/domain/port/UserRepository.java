package br.com.apirest.users.domain.port;

import br.com.apirest.users.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET name = :name, age = :age WHERE id = :id", nativeQuery = true)
    void editProduct(Integer id, String name, Integer age);

}
