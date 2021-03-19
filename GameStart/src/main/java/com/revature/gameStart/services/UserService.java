package com.revature.gameStart.services;

import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.Favorite;
import com.revature.gameStart.models.Game;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import com.revature.gameStart.repositories.UserRepository;
import com.revature.gameStart.util.PasswordEncryption;
import com.revature.gameStart.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User service class that has methods for calling the user repo and checking the validation of the data
 */
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * User service constructor that sets the user repository
     * @param userRepository user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    /**
     * gets a user from the database by their id
     * @param id user id
     * @return returns a user
     */
    @Transactional
    public User getUserById(int id){
        if (id <= 0) {
            throw new InvalidRequestException();
        }
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * register a new user in the database. checks if the user is valid else throw a resource persistence exception.
     * also sets the password to encrypted
     * @param newUser new user
     */
    @Transactional
    public void register(User newUser){
        if (!isUserValid(newUser)) throw new InvalidRequestException();

        if (getUserByUsername(newUser.getUsername()) != null) {
            throw new ResourcePersistenceException("Username is already in use");
        }

        // newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setPassword(PasswordEncryption.encryptString(newUser.getPassword()));

        userRepository.save(newUser);
    }

    /**
     * gets a list of all the users. checks if that list is not empty
     * @return returns a list of all the users
     */
    public List<User> getAllUsers(){

        List<User> userList;

        userList = (List<User>) userRepository.findAll();

        if (userList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return userList;
    }

    /**
     * finds a set of users by their role
     * @param role user role
     * @return returns a set of users
     */
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

    /**
     * get a user by the username and checks if the username is not empty
     * @param username username
     * @return returns an optional user
     */
    public User getUserByUsername(String username){
        if (username == null || username.trim().equals("")) {
            throw new InvalidRequestException();
        }
        Optional<User> user = userRepository.findUserByUsername(username);

      if (!user.isPresent())
        {
            return null;
        }
        else {
            return user.get();
        }
    }


    /**
     * checks to see if the user is in the database by their username and password and checks if their
     * password is encrypted. returns a principal
     * @param username username
     * @param password password
     * @return returns a authenticated user
     */
    public User authenticate(String username, String password) {

        System.out.println("I am in authenticate in UserService");
        User tempUser = getUserByUsername(username);
        System.out.println("I am in authenticate in UserService after");
//        if (!bCryptPasswordEncoder.matches(password, tempUser.getPassword())) {
//            // 401 code
//            return null;
//        }
        if(tempUser == null){
            throw new ResourceNotFoundException();
        }
        if (!PasswordEncryption.verifyPassword(password, tempUser.getPassword())) {
            System.out.println("The passwords were not the same!!!");
            return null;
        }

        return tempUser;
//        Principal principal = new Principal(tempUser);
//        System.out.println(principal.getId() + " " + principal.getUsername() + " " + principal.getRole() + " I am in authenticate");
//        System.out.println("I am in authenticate in UserService after Principal");
//        return  principal;
    }

    /**
     * gets a list of favorite game by the user id
     * @param userId user id
     * @return returns a list of favorite game made by a user
     */
    public List<Favorite> getFavoritesByUserId(int userId){
        if (userId <= 0 )
        {
            throw new InvalidRequestException();
        }

        List<Favorite> favoriteList = userRepository.findFavoriteByUserId(userId);

        return favoriteList;
    }

    /**
     * adds a favorite game under a user with a game id
     * @param userid user id
     * @param gameid game id
     */
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

    /**
     * delete a game in the user's favorite list by their user id and game id
     * @param userid user id
     * @param gameid game id
     */
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

    /**
     * get a sorted set of users
     * @param sortCriterion sort cirteria
     * @param usersForSorting user to sort
     * @return returns a sorted set of user
     */
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

    /**
     * update a user's profile and make sure the user's information is unique
     * @param updatedUser user being updated
     */
    public void updateProfile(User updatedUser){

        if(!isUserValid(updatedUser)) {
            throw new InvalidRequestException();
        }

        Optional<User> persistedUser = userRepository.findUserByUsername(updatedUser.getUsername());


        if(persistedUser.isPresent() && persistedUser.get().getId() != updatedUser.getId()) {
            throw new ResourcePersistenceException("That username is taken by someone else");
        }

        updatedUser.setPassword(PasswordEncryption.encryptString(updatedUser.getPassword()));


        userRepository.save(updatedUser);
    }

    /**
     * helper function to check if the user is a valid user
     * @param user user
     * @return returns true if the user is valid else false
     */
    public Boolean isUserValid(User user){
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getUsername().trim().equals("")) return false;
        return true;
    }


}