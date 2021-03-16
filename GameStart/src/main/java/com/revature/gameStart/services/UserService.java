package com.revature.gameStart.services;

import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import com.revature.gameStart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Transactional
    public User getUserById(int id){
        if (id <= 0) {
            throw new InvalidRequestException();
        }
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void register(User newUser){
        if (!isUserValid(newUser)) throw new InvalidRequestException();

        if (getUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Username is already in use");
        }

        // newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

    public List<User> getAllUsers(){

        List<User> userList;

        userList = (List<User>) userRepository.findAll();

        if (userList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return userList;
    }

    public Set<User> getUsersByRole(UserRole role){

        Set<User> usersSet;

        if (role == null) {
            throw new InvalidRequestException();
        }

        usersSet = userRepository.findUsersByRole(role);

        if (usersSet.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        System.out.println("The size of userset is " + usersSet.size());
        return usersSet;
    }

    public User getUserByUsername(String username){
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.orElse(null);
    }

    public Principal authenticate(String username, String password) {
        System.out.println("I am in authenticate in UserService");
        User tempUser = getUserByUsername(username);
        System.out.println("I am in authenticate in UserService after");
//        if (!bCryptPasswordEncoder.matches(password, tempUser.getPassword())) {
//            // 401 code
//            return null;
//        }


        Principal principal = new Principal(tempUser);
        System.out.println("I am in authenticate in UserService after Principal");
        return  principal;



    }

    public void addFavoriteGame(int userid, int gameid) {
        if (userid <= 0 || gameid <=0)
        {
            throw new InvalidRequestException();
        }

        Optional<Integer> optionalGameId = userRepository.findFavoriteByGameIdAndUserId( userid, gameid);

        if (optionalGameId.isPresent()) {
            throw new ResourcePersistenceException();
        }
        userRepository.InsertFavorite(userid, gameid);
    }

    public void deleteFavorite(int userid, int gameid) {
        if (userid <= 0 || gameid <=0)
        {
            throw new InvalidRequestException();
        }

        Optional<Integer> optionalGameId = userRepository.findFavoriteByGameIdAndUserId( userid, gameid);

        if (!optionalGameId.isPresent()) {
            throw new ResourceNotFoundException();
        }
        userRepository.DeleteFavorite(userid, gameid);
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
    //   Currently not able to test this because this requires a column for confirm account that we decided not to use
//    public Principal authenticate(String username, String password) throws AuthenticationException {
//        User authUser = userRepository.findUserByUsernameAndPassword(username, password).orElseThrow(AuthenticationException::new);
//
//        if (authUser != null){
//            Principal principal = new Principal(authUser);
//            String token = authClient
//        }
//        return null;
//    }

    public void updateProfile(User updatedUser){

        if(!isUserValid(updatedUser)) {
            throw new InvalidRequestException();
        }

        Optional<User> persistedUser = userRepository.findUserByUsername(updatedUser.getUsername());


        if(persistedUser.isPresent() && persistedUser.get().getId() != updatedUser.getId()) {
            throw new ResourcePersistenceException("That username is taken by someone else");
        }
        userRepository.save(updatedUser);
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