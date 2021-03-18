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

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    private final HttpServletResponse response;
    @Autowired
    public UserController(UserService userService, HttpSession httpSession,HttpServletResponse response){
        this.userService = userService;
        this.httpSession = httpSession;
        this.response = response;
    }

    // Get------------------------------------------------------------------------------

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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void RegisteredUser(@RequestBody User newUser){
        userService.register(newUser);
    }

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/favorite/{gameid}/{userId}")
    public void Favorite(@PathVariable int gameid, @PathVariable int userId) {
//        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
            userService.addFavoriteGame(userId, gameid);
//        }
//        else {
//            response.setStatus(403);
//        }
    }

    //DELETE--------------------------------------------------------------------------------------------------------

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

    //Logout
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/logout")
    public void logout(){
        httpSession.invalidate();
    }
}