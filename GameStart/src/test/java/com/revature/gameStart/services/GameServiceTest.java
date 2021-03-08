package com.revature.gameStart.services;

import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.GameRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GameServiceTest {
    //Setup ---------------------------------------------------------
    @InjectMocks
    GameService mockGameService;

    @Mock
    GameRepository mockGameRepo;

    List<Developer> devs = new ArrayList<>();
    List<Publisher> pubs = new ArrayList<>();
    List<Platform> plats = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    List<Game> games = new ArrayList<Game>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        devs.add(new Developer(1, "AppleDeveloper"));
        pubs.add(new Publisher(1, "BananaPublisher"));
        plats.add(new Platform(1, "CakePlatform"));
        genres.add(new Genre(1, "DogmaGenre"));

        games.add(new Game("ElfGame", genres, "GoodDescription", 5,
                devs, pubs, plats));
        games.add(new Game("HighGame", genres, "JokeDescription", 4,
                devs, pubs, plats));
        games.add(new Game("KillerGame", genres, "MediocreDescription", 3,
                devs, pubs, plats));
    }

    @After
    public void tearDown() throws Exception {
        devs.clear();
        pubs.clear();
        plats.clear();
        genres.clear();
    }


    //Tests ---------------------------------------------------------
    @Test
    public void testGetAllUsers() {
        //Arrange
        when(mockGameRepo.findAll()).thenReturn(games);

        //Act
        List<Game> testGames = mockGameService.getAllGames();

        //Assert
        assertEquals(3, testGames.size());
        verify(mockGameRepo, times(1)).findAll();
    }
}
