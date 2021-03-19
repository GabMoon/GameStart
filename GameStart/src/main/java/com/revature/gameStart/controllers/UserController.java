package com.revature.gameStart.controllers;

import com.revature.gameStart.dtos.Credentials;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.models.Favorite;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import com.revature.gameStart.services.UserService;
import com.revature.gameStart.util.RoleConverter;
import com.revature.gameStart.util.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // Get------------------------------------------------------------------------------

    //@Secured({"Admin", "Dev"})

    @GetMapping(path = "/id/{id}")
    public User UserById(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else if (!principal.getRole().equals("Admin")) {
            response.setStatus(403);
            return null;
        }
        else {
            response.setStatus(200);
            return userService.getUserById(id);
        }
//
//        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
//            return userService.getUserById(id);
//        }
//        else {
//            response.setStatus(403);
//            return null;
//        }
    }

    // @Secured({"Admin", "Dev"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> AllUsers(HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else if (!principal.getRole().equals("Admin")) {
            response.setStatus(403);
            return null;
        }
        else {
            response.setStatus(200);
            return userService.getAllUsers();

        }

//        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
//            return userService.getAllUsers();
//        }
//        else{
//            response.setStatus(403);
//            return null;
//        }

    }

    // @Secured({"Admin", "Dev"})
    @GetMapping(path = "/role/{userRole}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> UserByRole(@PathVariable String userRole, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else if (!principal.getRole().equals("Admin")) {
            response.setStatus(403);
            return null;
        }
        else {
            RoleConverter roleConverter = new RoleConverter();
            response.setStatus(200);
            return userService.getUsersByRole(roleConverter.convertToEntityAttribute(userRole));
        }

//        if (httpSession.getAttribute("userrole") == (UserRole.ADMIN.toString())) {
//            RoleConverter roleConverter = new RoleConverter();
//            return userService.getUsersByRole(roleConverter.convertToEntityAttribute(userRole));
//        }
//
//        else{
//            response.setStatus(403);
//            return null;
//        }
    }

    //@Secured({"Admin", "Dev"})
    @GetMapping(path="/username")
    public User UserByUsername(HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else {
           User existUser = userService.getUserByUsername(principal.getUsername());

           if (existUser != null) {
               response.setStatus(200);
               return existUser;
           }
           else {
               // When authentication is requires and has failed or has not yet been provided
               response.setStatus(401);
               return null;
           }
        }

//        User existUser = userService.getUserByUsername(httpSession.getAttribute("username").toString());
//        if (existUser != null) {
//
//            return existUser;
//        }
//        else{
//            //it throws null pointer exception
//            response.setStatus(403);
//            return null;
//        }
    }

    @GetMapping(path = "/myFavorite")
    public List<Favorite> getFavorite(HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
            return null;
        }
        else if (!principal.getRole().equals("Basic")) {
            response.setStatus(403);
            return null;
        }
        else {
            response.setStatus(200);
            return userService.getFavoritesByUserId(principal.getId());
        }

//
//        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            return userService.getFavoritesByUserId((Integer) httpSession.getAttribute("userid"));
//        }
//        else {
//            response.setStatus(403);
//            return null;
//        }
    }

    // Put-----------------------------------------------------------------------------------
    // @Secured({"Admin", "Dev", "Basic"})
    @PutMapping(path="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void UpdatedUser(@RequestBody User updatedUser, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else if (principal.getRole().equals("Admin") || principal.getRole().equals("Dev")) {

            userService.updateProfile(updatedUser);
            response.setStatus(201);
        }
        else {

            if (principal.getRole().equals("Basic") && principal.getId() == updatedUser.getId())
            {
                userService.updateProfile(updatedUser);
                response.setStatus(201);
            }
            else {
                response.setStatus(403);
            }
        }

//        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            userService.updateProfile(updatedUser);
//        }
//        else {
//            response.setStatus(403);
//        }
    }

    // Post-------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void RegisteredUser(@RequestBody User newUser, HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if(principal != null && principal.getRole().equals("Admin")) {
            userService.register(newUser);
        }
        else if (principal!= null) {
            response.setStatus(403);
        }
        else {
            userService.register(newUser);
        }

//        if (principal != null) {
//            response.setStatus(401);
//        }
//        else if (!principal.getRole().equals("Admin")) {
//            response.setStatus(403);
//            return null;
//        }
//        else {
//            return userService.getAllUsers();
//        }


        //userService.register(newUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Principal authenticateUser(@RequestBody @Valid Credentials credentials, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");



        if (principal != null) {
            return principal;
        }
        else {
            User authUser = userService.authenticate(credentials.getUsername(), credentials.getPassword());

            if (authUser == null) {
                response.setStatus(401);
                return null;
            }

            UserSession.getUserSession().createSession(request, authUser);

            Principal principal1 = new Principal(authUser);

            return principal1;
        }


//        Principal principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
//        //inserting user credentials into http session
//        httpSession.setAttribute("userid",principal.getId());
//        httpSession.setAttribute("username",principal.getUsername());
//        httpSession.setAttribute("userrole",principal.getRole());
//        return principal;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/favorite/{gameid}")
    public void Favorite(@PathVariable int gameid, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            userService.addFavoriteGame(principal.getId(), gameid);
        }

//
//        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            userService.addFavoriteGame((Integer) httpSession.getAttribute("userid"), gameid);
//        }
//        else {
//            response.setStatus(403);
//        }
    }

    //DELETE--------------------------------------------------------------------------------------------------------

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/favorite/delete/{gameid}")
    public void DeleteFavorite(@PathVariable int gameid, HttpServletRequest request, HttpServletResponse response) {

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(401);
        }
        else {
            userService.deleteFavorite(principal.getId(), gameid);
        }

//
//        if (httpSession.getAttribute("userrole") == (UserRole.BASIC.toString())) {
//            userService.deleteFavorite((Integer) httpSession.getAttribute("userid"), gameid);
//        }
//        else{
//            response.setStatus(403);
//        }
    }

    //Logout
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){

        UserSession.getUserSession().checkForUser(request);

        Principal principal = (Principal) request.getAttribute("principal");

        if (principal == null) {
            response.setStatus(403);
        }
        else {

            UserSession.getUserSession().logoutUser(request);
        }


       // httpSession.invalidate();
    }
}