package com.revature.gameStart.services;

import com.revature.gameStart.models.User;
import com.revature.gameStart.models.UserRole;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

class UserServiceTest {
    List<User> users = new ArrayList<>();

    @Before
    void setUp() {
        users.add(new User(1, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Banana", "Split", "BS", "Pass", "bs@amurica.com", UserRole.BASIC));
        users.add(new User(3, "Chocolate", "Cake", "CC", "Pass", "Cc@amurica.com", UserRole.BASIC));
    }

    @After
    void tearDown() {
        users.clear();
    }

    @Test
    void getUserById() {
        //Arrange

        //Act

        //Assert
    }
}