package com.revature.gameStart.services;

import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Service
@Transactional
public class UserService {

    @Autowired
    public UserService() {
        super();
    }

    public User getUserById(int id){
        if (id <= 0) {
            throw new InvalidRequestException();
        }

        return new User(3,"Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC);
    }

    public void register(User newUser){

    }

    public Set<User> getAllUsers(){

        return null;
    }

    public Set<User> getUsersByRole(){

        return null;
    }

    public User getUserByUsername(){

        return null;
    }

    public void confirmAccount(){

    }

    public SortedSet<User> sortUsers(){

        return null;
    }

    public Principal authenticate(){
        return null;
    }

    public void updateProfile(){

    }

    public Boolean isUserValid(){
        return false;
    }


}
