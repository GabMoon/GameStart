package com.revature.gameStart.services;


import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {
    public List<User> users = new ArrayList<>();

// I believe the way that the unit tests work is you set your unit test up, you call the method, and then you assert your expected result. I believe you should do that and then write your methods to make sure
    // they return what you need. You can start simple by returning exactly what it expects and then write your method to be more complex and become what you actually want it to do. This way you DO NOT write your
    // tests after your methods, else your tests will be biased and written in a way that makes it pass no matter what.
    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService();
        users.add(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        users.add(new User(3, "Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC));
    }

    @After
    public void tearDown() {
        users.clear();
    }

    // Test getUserById
    // Test that I get the right user and they equal
    @Test
    public void UserBanana() {

        //Arrange
        User user = users.get(2);
        //Act
        User testUser = userService.getUserById(2);

        //Assert
        assertEquals(user, testUser);
    }

    // Test that I get the wrong user and they do not equal
    @Test
    public void UserApple() {

        //Arrange
        User user = users.get(1);
        //Act
        User testUser = userService.getUserById(2);

        //Assert
        assert !testUser.equals(user) : "The users equal";
    }

    // Test that an exception is thrown when trying to get a user with an id less than 0
    @Test
    public void UserIdLessThanZero() {

        //Arrange
        User testUser = users.get(1);
        try {

            //Act
            testUser = userService.getUserById(-1);
            fail("Should have thrown an exception");
        }catch(InvalidRequestException e) {
            assertTrue(true);

        }
    }

    // Test Register



}