package com.revature.gameStart.repositories;

import com.revature.gameStart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   // @Query(value = "FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    //@Query(value = "FROM User u where u.role = :role")
    Set<User> findUsersByRole(String role);

    //@Query(value = "FROM User u where u.username = :username AND u.password = :password")
    Optional<User> findUserByUsernameAndPassword(String username, String password);

}
