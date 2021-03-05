package com.revature.gameStart.services;

import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.models.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Service
@Transactional
public class UserService {

    public UserService() {

    }

    public User getUserById(){

        return null;
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
