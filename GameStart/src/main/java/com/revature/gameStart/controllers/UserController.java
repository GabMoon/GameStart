package com.revature.gameStart.controllers;

import com.revature.gameStart.dtos.Credentials;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.models.Favorite;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import com.revature.gameStart.services.UserService;
import com.revature.gameStart.util.RoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * controller to manipulate the user
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    private final HttpServletResponse response;
    @Autowired

    /**
     * constructor for the user controller that sets the user service, http session, and response
     * @param userService user service
     * @param httpSession https session
     * @param response response
     */
    public UserController(UserService userService, HttpSession httpSession,HttpServletResponse response){
        this.userService = userService;
        this.httpSession = httpSession;
        this.response = response;
    }

    // Get------------------------------------------------------------------------------

    /**
     * an endpoint used to get a user by their id
     * @param id id of user
     * @return returns a user
     */
    //@Secured({"Admin", "Dev"})
    @GetMapping(path = "/id/{id}")
    public User UserById(@PathVariable int id) {

        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
            return userService.getUserById(id);
        }
        else {
            response.setStatus(403);
            return null;
        }
    }

    /**
     * default endpoint /users to get a list of all users
     * @return returns a list of all users
     */
    // @Secured({"Admin", "Dev"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> AllUsers() {

        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
            return userService.getAllUsers();
        }
        else{
            response.setStatus(403);
            return null;
        }

    }

    /**
     * an endpoint that get the user by their role
     * @param userRole the users role
     * @return returns the user's role
     */
    // @Secured({"Admin", "Dev"})
    @GetMapping(path = "/role/{userRole}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> UserByRole(@PathVariable String userRole) {

        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
            RoleConverter roleConverter = new RoleConverter();
            return userService.getUsersByRole(roleConverter.convertToEntityAttribute(userRole));
        }

        else{
            response.setStatus(403);
            return null;
        }
    }

    /**
     * an endpoint that returns a user by their username
     * @return returns a user
     */
    //@Secured({"Admin", "Dev"})
    @GetMapping(path="/username")
    public User UserByUsername() {
        User existUser = userService.getUserByUsername(httpSession.getAttribute("username").toString());
        if (existUser != null) {

            return existUser;
        }
        else{
            //it throws null pointer exception
            response.setStatus(403);
            return null;
        }
    }

    /**
     * an endpoint that returns a live of favorite by the user
     * @return returns a list of favorite
     */
    @GetMapping(path = "/myFavorite")
    public List<Favorite> getFavorite() {
        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            return userService.getFavoritesByUserId((Integer) httpSession.getAttribute("userid"));
        }
        else {
            response.setStatus(403);
            return null;
        }
    }

    // Put-----------------------------------------------------------------------------------

    /**
     * an endpoint that updates a user
     * @param updatedUser user being updated
     */
    // @Secured({"Admin", "Dev", "Basic"})
    @PutMapping(path="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void UpdatedUser(@RequestBody User updatedUser) {

        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            userService.updateProfile(updatedUser);
        }
        else {
            response.setStatus(403);
        }
    }

    // Post-------------------------------------------------------------------------------------

    /**
     * an endpoint that registers a new user
     * @param newUser the new user being register
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void RegisteredUser(@RequestBody User newUser){
        userService.register(newUser);
    }

    /**
     * an endpoint that authenticates a user by their username and password
     * @param credentials the credentials of the user
     * @return returns a principal
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Principal authenticateUser(@RequestBody @Valid Credentials credentials) {
        Principal principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        //inserting user credentials into http session
        httpSession.setAttribute("userid",principal.getId());
        httpSession.setAttribute("username",principal.getUsername());
        httpSession.setAttribute("userrole",principal.getRole());
        return principal;
    }

    /**
     * an endpoint that adds a game to the favorite list of a user
     * @param gameid game id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/favorite/{gameid}")
    public void Favorite(@PathVariable int gameid) {
        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            userService.addFavoriteGame((Integer) httpSession.getAttribute("userid"), gameid);
        }
        else {
            response.setStatus(403);
        }
    }

    //DELETE--------------------------------------------------------------------------------------------------------

    /**
     * an endpoint to delete a favorite game from a user by the game id
     * @param gameid game id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/favorite/delete/{gameid}")
    public void DeleteFavorite(@PathVariable int gameid) {
        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            userService.deleteFavorite((Integer) httpSession.getAttribute("userid"), gameid);
        }
        else{
            response.setStatus(403);
        }
    }

    /**
     * logs a user out by making session invalid
     */
    //Logout
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/logout")
    public void logout(){
        httpSession.invalidate();
    }
}