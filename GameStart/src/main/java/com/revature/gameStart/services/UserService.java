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
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    public ArrayList<User> users = new ArrayList<>();

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

    public List<User> getAllUsers(){

        List<User> userList;

        userList = users;

        if (users.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return userList;
    }

    public Set<User> getUsersByRole(UserRole role){

        Set<User> usersSet = new HashSet<>();

        if (role == null) {
            throw new InvalidRequestException();
        }

        for(User user : users){
            if (user.getRole() == role) {
                usersSet.add(user);
            }
        }

        if (usersSet.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return usersSet;
    }

    public User getUserByUsername(String username){
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }
        for(User user : users)
            if (user.getUsername().equals(username)) return user;
        throw new ResourceNotFoundException();
    }

    // Currently no way to test because this field does not exist in User. It is a column that exists in User, but not in the model
//    public void confirmAccount(int userId){
//
//        if (userId <= 0) {
//            throw new InvalidRequestException();
//        }
//
//
//
//    }

    public SortedSet<User> sortUsers(String sortCriterion, Set<User> usersForSorting){

        // DOES NOT WORK. I AM NOT ACTUALLY SORTING ANYTHING
        SortedSet<User> usersSet = new TreeSet<>(Comparator.comparing(User:: getId, Integer:: compareTo));

        switch(sortCriterion.toLowerCase()) {
            case "username":
                usersSet = usersSet.stream()
                        .collect(Collectors.toCollection(() -> {
                            return new TreeSet<>(Comparator.comparing(User::getUsername, String::compareTo));
                        }));
                break;
            case "first":
                usersSet = usersSet.stream()
                        .collect(Collectors.toCollection(() -> {
                            return new TreeSet<>(Comparator.comparing(User::getFirstName, String::compareTo));
                        }));
                break;
            case "last":
                usersSet = usersSet.stream()
                        .collect(Collectors.toCollection(() -> {
                            return new TreeSet<>(Comparator.comparing(User::getLastName, String::compareTo));
                        }));
                break;
            case "role":
                usersSet = usersSet.stream()
                        .collect(Collectors.toCollection(() -> {
                            return new TreeSet<>(Comparator.comparing(User:: getRole, Enum:: compareTo));
                        }));
                break;
            default:
                throw new InvalidRequestException();
        }

        return usersSet;
    }
    // Currently not able to test this because this requires a column for confirm account that we decided not to use
//    public Principal authenticate(String username, String password){
//
//
//        return null;
//    }

    public void updateProfile(User updatedUser){

        if(!isUserValid(updatedUser)) {
            throw new InvalidRequestException();
        }

        Optional<User> persistedUser = Optional.empty();

        for(User user : users) {
            if(user.getUsername().equals(updatedUser.getUsername())) {
                persistedUser = Optional.of(user);
            }
        }

        if(persistedUser.isPresent() && persistedUser.get().getId() != updatedUser.getId()) {
            throw new ResourcePersistenceException("That username is taken by someone else");
        }

        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
            }
        }

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
