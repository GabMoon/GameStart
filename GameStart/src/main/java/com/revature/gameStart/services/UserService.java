package com.revature.gameStart.services;

import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService {

    public List<User> users = new ArrayList<>();

    @Autowired
    public UserService() {
        super();
        users.add(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        users.add(new User(3, "Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC));
    }

    public User getUserById(int id){
        if (id <= 0) {
            throw new InvalidRequestException();
        }

        return new User(3,"Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC);
    }

    public void register(User newUser){
        if (!isUserValid(newUser)) throw new InvalidRequestException();

        if (getUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Username is already in use");
        }

        users.add(newUser);

    }

    public Set<User> getAllUsers(){

        return null;
    }

    public Set<User> getUsersByRole(){

        return null;
    }

    public User getUserByUsername(String username){
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }
        for(User user : users)
            if (user.getUsername().equals(username)) return user;
        throw new ResourceNotFoundException();
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

    public Boolean isUserValid(User user){
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getUsername().trim().equals("")) return false;
        return true;
    }


}
