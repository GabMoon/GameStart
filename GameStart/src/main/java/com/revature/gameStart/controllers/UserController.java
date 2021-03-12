package com.revature.gameStart.controllers;

import com.revature.gameStart.dtos.Credentials;
import com.revature.gameStart.dtos.Principal;
import com.revature.gameStart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

//    @PostMapping(path = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Principal authenticateUser(@RequestBody @Valid Credentials credentials, HttpServletResponse response) {
//        Principal principal = userService.authenticate(credentials.getUsername(), credentials.getPassword());
//        response.addCookie(new Cookie("quizzard-token", principal.getToken()));
//        return principal;
//    }
}
