package com.revature.gameStart.services;


import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import static org.junit.Assert.*;

import com.revature.gameStart.repositories.UserRepository;
import io.micrometer.core.instrument.config.validate.Validated;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

import java.util.*;

public class UserServiceTest {


    @Mock
    UserRepository mockUserRepository;

    @Spy
    UserService spyUserService;

    @Spy
    UserRepository spyUserRepository;

    @InjectMocks
    UserService mockUserService;




    public ArrayList<User> users = new ArrayList<>();
    Optional<User> optionalBanana;
    Optional<User> doesNotExistId4;
    Optional<User> doesNotExistIdNegative1;
    Optional<User> optionalAPUsername;
    Optional<User> optionalUserDoesNotExist;
    Optional<Integer> gameIdOptional;
    Optional<Integer> gameIdOptionalExists;
    User aUser;
    public ArrayList<User> emptyUsers = new ArrayList<>();
    public Set<User> basicUsers = new HashSet<>();
    public Set<User> adminUsers = new HashSet<>();
    User userThatExists;

// I believe the way that the unit tests work is you set your unit test up, you call the method, and then you assert your expected result. I believe you should do that and then write your methods to make sure
    // they return what you need. You can start simple by returning exactly what it expects and then write your method to be more complex and become what you actually want it to do. This way you DO NOT write your
    // tests after your methods, else your tests will be biased and written in a way that makes it pass no matter what.

    @Before
    public void setUp() {
        spyUserService = new UserService(mockUserRepository);
        MockitoAnnotations.initMocks(this);




        users.add(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        users.add(new User(3, "Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC));
        optionalBanana = Optional.of(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        doesNotExistId4 = Optional.empty();
        doesNotExistIdNegative1 = Optional.empty();
        optionalAPUsername = Optional.of(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        optionalUserDoesNotExist = Optional.empty();
        aUser = new User(4, "User4", "Last4", "us4", "ps4","us4@email.com", UserRole.BASIC);
        basicUsers.add(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        basicUsers.add(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        basicUsers.add(new User(3, "Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC));
        userThatExists = new User(6, "NewUser", "NewLastUser", "AP", "AnyPass", "NewU@email.com", UserRole.BASIC);
        gameIdOptional = Optional.empty();
        gameIdOptionalExists = Optional.of(1);
    }

    @After
    public void tearDown() {
        users.clear();

    }

    //        // Arrange
//        when(mockUserRepository.findAll()).thenReturn(users);
//            // Nothing to arrange
//        // Act
//        List<User> testUsers= mockUserService.getAllUsers();
//        // Assert
//        assertEquals(3, testUsers.size());
//        verify(mockUserRepository, times(1)).findAll();

    // --------------------------------------------------------------------- getUserById----------------------------------------------------------------------------
    // Test that I get the right user and they equal
    @Test @Ignore
    public void UserBanana() {

        //Arrange
        when(mockUserRepository.findById(2)).thenReturn(optionalBanana);
        //Act
        User testUser = mockUserService.getUserById(2);
        //Assert
        //assertNotNull(testUser);
        //assertEquals(users.get(1),testUser);
        verify(mockUserRepository, times(1)).findById(2);
    }

    // Test that I get a user that does not exist
    @Test(expected = ResourceNotFoundException.class)
    public void UserApple() {

        //Arrange
        when(mockUserRepository.findById(4)).thenReturn(doesNotExistId4);
        //Act
        User testUser = mockUserService.getUserById(4);
        //Assert
        verify(mockUserRepository, times(1)).findById(4);
    }

    // Test that an exception is thrown when trying to get a user with an id less than 0
    @Test(expected = InvalidRequestException.class)
    public void UserIdLessThanZero() {

        //Arrange
        when(mockUserRepository.findById(-1)).thenReturn(doesNotExistIdNegative1);
        //Act
        User testUser = mockUserService.getUserById(-1);
        //Assert
        verify(mockUserRepository, times(0)).findById(-1);
    }

    //---------------------------------------------------------getUserByUsername------------------------------------------------------------------------------------------------------------
    // Test that a user is returned
    @Test @Ignore
    public void UserAP() {

        //Arrange
        when(mockUserRepository.findUserByUsername("AP")).thenReturn(optionalAPUsername);
        //Act
        User testUser = mockUserService.getUserByUsername("AP");
        //Assert
        assertNotNull(testUser);
        assertEquals(users.get(0),testUser);
        verify(mockUserRepository, times(1)).findUserByUsername("AP");
    }
    // Test that InvalidRequestException is thrown if username is null
    @Test(expected = InvalidRequestException.class)
    public void UsernameIsNull() {

        //Arrange
        //when(mockUserRepository.findUserByUsername(null)).thenReturn(optionalUser);
        //Act
        User testUser = mockUserService.getUserByUsername(null);
        // Assert
            // Don't have to assert because an exception is expected
//        assertNotNull(testUser);
//        assertEquals(users.get(0),testUser);
        verify(mockUserRepository, times(1)).findUserByUsername("AP");

    }
    // Test that InvalidRequestException is thrown if username is empty string
    @Test(expected = InvalidRequestException.class)
    public void UsernameIsEmptyString() {

        //Arrange
        //when(mockUserRepository.findUserByUsername("")).thenReturn(optionalUser);
        //Act
        User testUser = mockUserService.getUserByUsername("");
        // Assert
        // Don't have to assert because an exception is expected
//        assertNotNull(testUser);
//        assertEquals(users.get(0),testUser);
        verify(mockUserRepository, times(0)).findUserByUsername("");
    }
    // Test that ResourceNotFoundException is thrown when user does not exist
    @Test @Ignore
    public void UserNotFound() {

        //Arrange
        when(mockUserRepository.findUserByUsername("Anything")).thenReturn(optionalUserDoesNotExist);
        //Act
        User testUser = mockUserService.getUserByUsername("Anything");
        //Assert
        assertNull(testUser);
        verify(mockUserRepository, times(1)).findUserByUsername("Anything");
    }

    // -------------------------------------------------------Register User-----------------------------------------------------------------------------------------------------------------------
    // Test that a user is added to the list
    @Test @Ignore
    public void UserRegistered() {


        //Arrange
        User newUser = new User(4, "User4", "Last4", "us4", "ps4","us4@email.com", UserRole.BASIC);
        doReturn(true).when(spyUserService).isUserValid(new User(4, "User4", "Last4", "us4", "ps4","us4@email.com", UserRole.BASIC));
        doReturn(null).when(spyUserService).getUserByUsername("us4");
        when(mockUserRepository.save(newUser)).thenReturn(aUser);
        //Act
        spyUserService.register(newUser);
        //Assert
        verify(mockUserRepository, times(1)).save(newUser);
    }

    // Test that an InvalidRequestException is thrown
    @Test(expected = InvalidRequestException.class)
    public void UserNotRegisteredInvalidRequestException() {

        //Arrange
        User newUser = null;
        when(mockUserRepository.save(newUser)).thenReturn(aUser);
        //Act
        mockUserService.register(newUser);
        //Assert
        verify(mockUserRepository, times(0)).save(newUser);
    }

    // Test that a ResourcePersistenceException is thrown
    @Test(expected = ResourcePersistenceException.class)
    public void UsernameInUse() {
        //Arrange
        User newUser = new User(6, "NewUser", "NewLastUser", "AP", "AnyPass", "NewU@email.com", UserRole.BASIC);
        doReturn(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC)).when(spyUserService).getUserByUsername("AP");
        when(mockUserRepository.save(newUser)).thenReturn(aUser);
        //Act
        spyUserService.register(newUser);
        //Assert
        verify(mockUserRepository, times(0)).save(newUser);
    }

// ---------------------------------------------- getAllUsers()---------------------------------------------------------------------------------------------
    // Test that gets all users
    @Test @Ignore
    public void getUsers() {
        // Arrange
        when(mockUserRepository.findAll()).thenReturn(users);
            // Nothing to arrange
        // Act
        List<User> testUsers= mockUserService.getAllUsers();
        // Assert
        assertEquals(3, testUsers.size());
        verify(mockUserRepository, times(1)).findAll();
    }

    // Test that ResourceNotFoundException is thrown
    @Test(expected = ResourceNotFoundException.class)
    public void noUsersInList() {
        // Arrange
        when(mockUserRepository.findAll()).thenReturn(emptyUsers);
        // Nothing to arrange
        // Act
        List<User> testUsers= mockUserService.getAllUsers();
        // Assert
        verify(mockUserRepository, times(1)).findAll();
    }

    //-----------------------------------------------getUsersByRole--------------------------------------------------------------------------------------------------------------------
    // Test that gets Basic Users
    @Test @Ignore
    public void getBasicUsers() {
//        Set<User> usersSet = new HashSet<>();
//
//        usersSet.add(users.get(0));
//        usersSet.add(users.get(1));
//        usersSet.add(users.get(2));
//
//
//        Optional<User> existingUser = Optional.of( new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
//        doReturn(existingUser).when(spyUserRepository).findUserByUsername("AP");


        // Arrange
        when(mockUserRepository.findUsersByRole(UserRole.BASIC)).thenReturn(basicUsers);
        // Nothing to arrange
        // Act
        Set<User> testUsers= mockUserService.getUsersByRole(UserRole.BASIC);
        // Assert
        assertEquals(3, testUsers.size());
        verify(mockUserRepository, times(1)).findUsersByRole(UserRole.BASIC);
    }
    // Test that throws InvalidRequestException
    @Test(expected = InvalidRequestException.class)
    public void getNullUsers() {

        // Arrange
        UserRole userRole = null;
        //when(mockUserRepository.findUsersByRole(UserRole.BASIC.toString())).thenReturn(basicUsers);
        // Nothing to arrange
        // Act
        Set<User> testUsers= mockUserService.getUsersByRole(userRole);
        // Assert
        verify(mockUserRepository, times(0)).findUsersByRole(null);
    }
    // Test that throws ResourceNotFoundException
    @Test(expected = ResourceNotFoundException.class)
    public void getNoUsers() {

        // Arrange
        //when(mockUserRepository.findUsersByRole(UserRole.BASIC.toString())).thenReturn(adminUsers);
        // Nothing to arrange
        // Act
        Set<User> testUsers= mockUserService.getUsersByRole(UserRole.ADMIN);
        // Assert
        //verify(mockUserRepository, times(1)).findUsersByRole(UserRole.ADMIN.toString());
    }

//-------------------------------------------------sortUsers-------------------------------------------------------------------------------------------
    // Test that get users sorted by username
    @Test
    public void getUsersSortUsername(){

        //Arrange
        String sortCriterion = "username";
        Set<User> usersForSorting = new HashSet<>(users);
        //Act
        SortedSet<User> sortedUsers= mockUserService.sortUsers(sortCriterion, usersForSorting);
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
        Set<User> usersForSorting = new HashSet<>(users);
        //Act
        SortedSet<User> sortedUsers= mockUserService.sortUsers(sortCriterion, usersForSorting);
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
        Set<User> usersForSorting = new HashSet<>(users);
        //Act
        SortedSet<User> sortedUsers= mockUserService.sortUsers(sortCriterion, usersForSorting);
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
        Set<User> usersForSorting = new HashSet<>(users);
        //Act
        SortedSet<User> sortedUsers= mockUserService.sortUsers(sortCriterion, usersForSorting);
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
        Set<User> usersForSorting = new HashSet<>(users);
        //Act
        SortedSet<User> sortedUsers= mockUserService.sortUsers(sortCriterion, usersForSorting);
        // Assert
            // Nothing to assert because we expect an exception
    }

//-----------------------------------updateProfile----------------------------------------------------------------------
    // Test that updates a user that exists
    @Test @Ignore
    public void updateAUserThatExists(){
        //Arrange
        User updatedUser = new User(6, "NewUser", "NewLastUser", "AP", "AnyPass", "NewU@email.com", UserRole.BASIC);
        when(mockUserRepository.save(updatedUser)).thenReturn(userThatExists);
        //Act
        mockUserService.updateProfile(updatedUser);
        //Assert
        assertEquals(updatedUser, userThatExists);
        verify(mockUserRepository, times(1)).save(updatedUser);
    }

    // Test that the updated is not valid
    @Test(expected = InvalidRequestException.class)
    public void updateAUserNotValid() {
        // Arrange
        User updatedUser = new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC);
        updatedUser.setFirstName("");
        // Act
        mockUserService.updateProfile(updatedUser);
        // Assert
        verify(mockUserRepository, times(0)).save(updatedUser);
            // Nothing to assert because we expect an exception
    }

    // Test that the username is already taken
    @Test(expected = ResourcePersistenceException.class) @Ignore
    public void updatedUserHasAUsernameThatIsAlreadyTaken(){
//        //Arrange
//        User newUser = new User(4, "User4", "Last4", "us4", "ps4","us4@email.com", UserRole.BASIC);
//        doReturn(null).when(spyUserService).getUserByUsername("us4");
//        when(mockUserRepository.save(newUser)).thenReturn(aUser);
//        //Act
//        spyUserService.register(newUser);
//        //Assert
//        verify(mockUserRepository, times(1)).save(newUser);

        // Arrange
        Optional<User> existingUser = Optional.of( new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        doReturn(existingUser).when(spyUserRepository).findUserByUsername("AP");

        User updatedUser = new User(5, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC);
        // Act
        mockUserService.updateProfile(updatedUser);
        // Assert
        verify(mockUserRepository, times(0)).save(updatedUser);
            // Nothing to assert because we expect an exception
    }

//-----------------------------------------------------addFavoriteGame---------------------------------------------------------------------------------------------------------------

    // Test where Adding favorite game works
    @Test @Ignore
    public void addFavoriteGame() {

        // Arrange
        when(mockUserRepository.findFavoriteByGameIdAndUserId(1,1)).thenReturn(gameIdOptional);
        // Act
        mockUserService.addFavoriteGame(1, 1);

        // Assert
        verify(mockUserRepository, times(1)).findFavoriteByGameIdAndUserId(1,1);
        verify(mockUserRepository, times(1)).InsertFavorite(1,1);
    }
    // Test where Invalid Request Exception occurs because of userid = 0
    @Test(expected = InvalidRequestException.class)
    public void addFavoriteGameInvalidRequestExceptionUserIdIsZero() {

        // Arrange

        // Act
        mockUserService.addFavoriteGame(0,1);
        // Assert
        verify(mockUserRepository, times(0)).findFavoriteByGameIdAndUserId(0,1);
        verify(mockUserRepository, times(0)).InsertFavorite(0,1);
    }

    // Test where InvalidRequest Exception occurs because of gameid = 0
    @Test(expected = InvalidRequestException.class)
    public void addFavoriteGameInvalidRequestExceptionGameIdIsZero() {

        // Arrange

        // Act
        mockUserService.addFavoriteGame(1,0);
        // Assert
        verify(mockUserRepository, times(0)).findFavoriteByGameIdAndUserId(1,0);
        verify(mockUserRepository, times(0)).InsertFavorite(1,0);

    }

    // Test where ResourcePersistenceException occurs because of the resource existing in the optional
    @Test(expected = ResourcePersistenceException.class) @Ignore
    public void addFavoriteGameResourcePersistenceException() {

        // Arrange
        when(mockUserRepository.findFavoriteByGameIdAndUserId(1,1)).thenReturn(gameIdOptionalExists);
        // Act
        mockUserService.addFavoriteGame(1, 1);

        // Assert
        verify(mockUserRepository, times(1)).findFavoriteByGameIdAndUserId(1,1);
        verify(mockUserRepository, times(0)).InsertFavorite(1,1);

    }

//-----------------------------------------------------deleteFavoriteGame---------------------------------------------------------------------------------------------------------------

    // Test where Deleting favorite game works
    @Test @Ignore
    public void deleteFavoriteGame() {

        // Arrange
        when(mockUserRepository.findFavoriteByGameIdAndUserId(1,1)).thenReturn(gameIdOptionalExists);
        // Act
        mockUserService.deleteFavorite(1, 1);

        // Assert
        verify(mockUserRepository, times(1)).findFavoriteByGameIdAndUserId(1,1);
        verify(mockUserRepository, times(1)).DeleteFavorite(1,1);
    }
    // Test where Invalid Request Exception occurs because of userid = 0
    @Test(expected = InvalidRequestException.class)
    public void deleteFavoriteGameInvalidRequestExceptionUserIdIsZero() {

        // Arrange

        // Act
        mockUserService.deleteFavorite(0,1);
        // Assert
        verify(mockUserRepository, times(0)).findFavoriteByGameIdAndUserId(0,1);
        verify(mockUserRepository, times(0)).DeleteFavorite(0,1);
    }

    // Test where InvalidRequest Exception occurs because of gameid = 0
    @Test(expected = InvalidRequestException.class)
    public void deleteFavoriteGameInvalidRequestExceptionGameIdIsZero() {

        // Arrange

        // Act
        mockUserService.deleteFavorite(1,0);
        // Assert
        verify(mockUserRepository, times(0)).findFavoriteByGameIdAndUserId(1,0);
        verify(mockUserRepository, times(0)).DeleteFavorite(1,0);

    }

    // Test where ResourceNotFoundException occurs because of the resource not existing in the optional
    @Test(expected = ResourceNotFoundException.class)
    public void FavoriteGameResourceNotFoundException() {

        // Arrange
        when(mockUserRepository.findFavoriteByGameIdAndUserId(1,1)).thenReturn(gameIdOptional);
        // Act
        mockUserService.deleteFavorite(1, 1);

        // Assert
        verify(mockUserRepository, times(1)).findFavoriteByGameIdAndUserId(1,1);
        verify(mockUserRepository, times(0)).DeleteFavorite(1,1);

    }
}