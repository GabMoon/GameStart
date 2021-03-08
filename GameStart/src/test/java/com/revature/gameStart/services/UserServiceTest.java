package com.revature.gameStart.services;


import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import static org.junit.Assert.*;

import io.micrometer.core.instrument.config.validate.Validated;
import org.junit.*;

import java.util.*;

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
    @Test(expected = InvalidRequestException.class)
    public void UserIdLessThanZero() {

        //Arrange
        User testUser;


        //Act
        testUser = userService.getUserById(-1);

        // Assert
            // Do not have to assert

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

// ---------------------------------------------- getAllUsers()---------------------------------------------------------------------------------------------
    // Test that gets all users
    @Test
    public void getUsers() {
        // Arrange
            // Nothing to arrange
        // Act
        List<User> allUsers = userService.getAllUsers();
        // Assert
        assertTrue(!allUsers.isEmpty());
    }

    // Test that ResourceNotFoundException is thrown
    @Test(expected = ResourceNotFoundException.class)
    public void noUsersInList() {
        // Arrange
            userService.users.clear();
        // Act
        List<User> allUsers = userService.getAllUsers();

        // Assert
            // Nothing to assert because an exception is expected
    }

    //-----------------------------------------------getUsersByRole--------------------------------------------------------------------------------------------------------------------
    // Test that gets Basic Users
    @Test
    public void getBasicUsers() {
        // Arrange
        UserRole userRole = UserRole.BASIC;
        // Act
        Set<User> usersSetRole = userService.getUsersByRole(userRole);
        // Assert
        assertTrue(!usersSetRole.isEmpty());
    }
    // Test that throws InvalidRequestException
    @Test(expected = InvalidRequestException.class)
    public void getNullUsers() {
        // Arrange
        UserRole userRole = null;
        // Act
        Set<User> userSetRole = userService.getUsersByRole(userRole);
        // Assert
            // Nothing to assert because an exception is expected
    }
    // Test that throws ResourceNotFoundException
    @Test(expected = ResourceNotFoundException.class)
    public void getNoUsers() {
        // Arrange
        UserRole userRoleAdmin = UserRole.ADMIN;
        // Act
        Set<User> userSetRole = userService.getUsersByRole(userRoleAdmin);
        // Assert
            // Nothing to assert because an exception is expected
    }

//-------------------------------------------------sortUsers-------------------------------------------------------------------------------------------
    // Test that get users sorted by username
    @Test
    public void getUsersSortUsername(){
        //Arrange
        String sortCriterion = "username";
        Set<User> usersForSorting = new HashSet<>(userService.users);
        //Act
        SortedSet<User> sortedUsers= userService.sortUsers(sortCriterion, usersForSorting);
        // Assert
        Iterator iterator = sortedUsers.iterator();
        User currentUser = null;
        User nextUser = null;
        while(iterator.hasNext()) {

            if (currentUser == null) {
                currentUser = (User)iterator.next();
            }
            else {
                nextUser = (User)iterator.next();
                if(nextUser.getUsername().compareTo(currentUser.getUsername()) < 0) {
                    fail();
                    break;
                }
                currentUser = nextUser;
            }
        }

        assertTrue(true);
    }
    // Test that get users sorted by First Name
    @Test
    public void getUsersSortFirstName(){
        //Arrange
        String sortCriterion = "first";
        Set<User> usersForSorting = new HashSet<>(userService.users);
        //Act
        SortedSet<User> sortedUsers= userService.sortUsers(sortCriterion, usersForSorting);
        // Assert
        Iterator iterator = sortedUsers.iterator();
        User currentUser = null;
        User nextUser = null;
        while(iterator.hasNext()) {

            if (currentUser == null) {
                currentUser = (User)iterator.next();
            }
            else {
                nextUser = (User)iterator.next();
                if(nextUser.getFirstName().compareTo(currentUser.getFirstName()) < 0) {
                    fail();
                    break;
                }
                currentUser = nextUser;
            }
        }

        assertTrue(true);
    }
    // Test that get users sorted by Last Name
    @Test
    public void getUsersSortLastName(){
        //Arrange
        String sortCriterion = "last";
        Set<User> usersForSorting = new HashSet<>(userService.users);
        //Act
        SortedSet<User> sortedUsers= userService.sortUsers(sortCriterion, usersForSorting);
        // Assert
        Iterator iterator = sortedUsers.iterator();
        User currentUser = null;
        User nextUser = null;
        while(iterator.hasNext()) {

            if (currentUser == null) {
                currentUser = (User)iterator.next();
            }
            else {
                nextUser = (User)iterator.next();
                if(nextUser.getLastName().compareTo(currentUser.getLastName()) < 0) {
                    fail();
                    break;
                }
                currentUser = nextUser;
            }
        }

        assertTrue(true);
    }
    // Test that get users sorted by role
    @Test
    public void getUsersSortRole(){
        //Arrange
        String sortCriterion = "role";
        Set<User> usersForSorting = new HashSet<>(userService.users);
        //Act
        SortedSet<User> sortedUsers= userService.sortUsers(sortCriterion, usersForSorting);
        // Assert
        Iterator iterator = sortedUsers.iterator();
        User currentUser = null;
        User nextUser = null;
        while(iterator.hasNext()) {

            if (currentUser == null) {
                currentUser = (User)iterator.next();
            }
            else {
                nextUser = (User)iterator.next();
                if(nextUser.getRole().compareTo(currentUser.getRole()) < 0) {
                    fail();
                    break;
                }
                currentUser = nextUser;
            }
        }

        assertTrue(true);
    }
    // Test that throws InvalidRequestException
    @Test(expected = InvalidRequestException.class)
    public void getUsersThrowException(){
        //Arrange
        String sortCriterion = "anything";
        Set<User> usersForSorting = new HashSet<>(userService.users);
        //Act
        SortedSet<User> sortedUsers= userService.sortUsers(sortCriterion, usersForSorting);
        // Assert
            // Nothing to assert because we expect an exception
    }

//-----------------------------------updateProfile----------------------------------------------------------------------
    // Test that updates a user that exists
    @Test
    public void updateAUserThatExists(){
        // Arrange
        User previousUser = new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC);
        User updatedUser = new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC);
        updatedUser.setFirstName("Different");
        // Act
        userService.updateProfile(updatedUser);
        // Assert
        assertNotEquals(previousUser.getFirstName(), userService.users.get(0).getFirstName());
    }

    // Test that the updated is not valid
    @Test(expected = InvalidRequestException.class)
    public void updateAUserNotValid() {
        // Arrange
        User updatedUser = userService.users.get(0);
        updatedUser.setFirstName("");
        // Act
        userService.updateProfile(updatedUser);
        // Assert
            // Nothing to assert because we expect an exception
    }

    // Test that the username is already taken
    @Test(expected = ResourcePersistenceException.class)
    public void updatedUserHasAUsernameThatIsAlreadyTaken(){
        // Arrange

        User updatedUser = new User(5, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC);
        // Act
        userService.updateProfile(updatedUser);
        // Assert
            // Nothing to assert because we expect an exception
    }
}