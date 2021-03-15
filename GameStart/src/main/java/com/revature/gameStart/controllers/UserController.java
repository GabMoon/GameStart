package com.revature.gameStart.controllers;

import com.revature.gameStart.dtos.Credentials;
import com.revature.gameStart.dtos.Principal;
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
    public User UserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // @Secured({"Admin", "Dev"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> AllUsers() {
        return userService.getAllUsers();
    }

    // @Secured({"Admin", "Dev"})
    @GetMapping(path = "/role/{userRole}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<User> UserByRole(@PathVariable String userRole) {
        RoleConverter roleConverter = new RoleConverter();

        return userService.getUsersByRole(roleConverter.convertToEntityAttribute(userRole));
    }
    //@Secured({"Admin", "Dev"})
    @GetMapping(path="/username/{username}")
    public User UserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    // Put-----------------------------------------------------------------------------------
    // @Secured({"Admin", "Dev", "Basic"})
    @PutMapping(path="/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void UpdatedUser(@RequestBody User updatedUser) {
        userService.updateProfile(updatedUser);
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
        System.out.println("I am in authenticateUser");
        Principal principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        //response.addCookie(new Cookie("quizzard-token", principal.getToken()));
        System.out.println("The Principal is " + principal.getId() + " " + principal.getUsername() + " " + principal.getRole().toString());
        return principal;
    }
}