package com.revature.gameStart.services;


import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import static org.junit.Assert.*;

import io.micrometer.core.instrument.config.validate.Validated;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {


// I believe the way that the unit tests work is you set your unit test up, you call the method, and then you assert your expected result. I believe you should do that and then write your methods to make sure
    // they return what you need. You can start simple by returning exactly what it expects and then write your method to be more complex and become what you actually want it to do. This way you DO NOT write your
    // tests after your methods, else your tests will be biased and written in a way that makes it pass no matter what.
    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService();

    }

    @After
    public void tearDown() {
        userService = null;

    }

    // --------------------------------------------------------------------- getUserById----------------------------------------------------------------------------
    // Test that I get the right user and they equal
    @Test
    public void UserBanana() {

        //Arrange
        User user = userService.users.get(2);
        //Act
        User testUser = userService.getUserById(2);

        //Assert
        assertEquals(user, testUser);
    }

    // Test that I get the wrong user and they do not equal
    @Test
    public void UserApple() {

        //Arrange
        User user = userService.users.get(1);
        //Act
        User testUser = userService.getUserById(2);

        //Assert
        assert !testUser.equals(user) : "The users equal";
    }

    // Test that an exception is thrown when trying to get a user with an id less than 0
    @Test
    public void UserIdLessThanZero() {

        //Arrange
        User testUser = userService.users.get(1);
        try {

            //Act
            testUser = userService.getUserById(-1);
            fail("Should have thrown an exception");
        }catch(InvalidRequestException e) {
            assertTrue(true);

        }
    }

    //---------------------------------------------------------getUserByUsername------------------------------------------------------------------------------------------------------------
    // Test that a user is returned
    @Test
    public void UserAP() {
        // Arrange
        String username = "AP";
        // Act
        User ap = userService.getUserByUsername(username);
        // Assert
        assertNotNull(ap);
    }
    // Test that InvalidRequestException is thrown if username is null
    @Test(expected = InvalidRequestException.class)
    public void UsernameIsNull() {

        // Act
        User user = userService.getUserByUsername(null);

        // Assert
        // Don't have to assert because an exception is expected
    }
    // Test that InvalidRequestException is thrown if username is empty string
    @Test(expected = InvalidRequestException.class)
    public void UsernameIsEmptyString() {

        // Arrange
        String username = "";
        // Act
        User user = userService.getUserByUsername(username);

        // Assert
        // Don't have to assert because an exception is expected
    }
    @Test(expected = ResourceNotFoundException.class)
    public void UserNotFound() {
        // Arrange
        String username = "BB";
        // Act
        User user = userService.getUserByUsername(username);

        // Assert
        // Don't have to assert because an exception is expected
    }

    // -------------------------------------------------------Register User-----------------------------------------------------------------------------------------------------------------------
    // Test that a user is added to the list
    @Test
    public void UserRegistered() {
        // Arrange
        User user = new User(4, "User4", "Last4", "us4", "ps4","us4@email.com", UserRole.BASIC);
        // Act
        userService.register(user);
        // Assert
        assertTrue(userService.users.contains(user));
    }

    // Test that an InvalidRequestException is thrown
    @Test(expected = InvalidRequestException.class)
    public void UserNotRegisteredInvalidRequestException() {
        // Arrange
        User user = null;
        // Act
        userService.register(user);
        // Assert
        // Already asserted near @Test. We are expecting an InvalidRequestException
    }

    // Test that a ResourcePersistenceException is thrown
    @Test(expected = ResourcePersistenceException.class)
    public void UsernameInUse() {
        // Arrange
        User user = new User(6, "NewUser", "NewLastUser", "AP", "AnyPass", "NewU@email.com", UserRole.BASIC);
        // Act
        userService.register(user);

        // Assert
        // Already asserted new @Test. We are expecting a ResourcePersistenceException
    }

}