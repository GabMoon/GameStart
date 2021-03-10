package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

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
    List<Game> emptyGames;

    Optional<Game> gameWithIdZeroOptional;
    Game gameWithIdZero;

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

        emptyGames = new ArrayList<>();

        gameWithIdZero = new Game("MyGame", genres, "Description", 6, devs, pubs, plats);
        gameWithIdZero.setId(0);

        //gameWithIdZeroOptional = Optional.of()
    }

    @After
    public void tearDown() throws Exception {
        devs.clear();
        pubs.clear();
        plats.clear();
        genres.clear();
        emptyGames.clear();
    }


    //Tests ---------------------------------------------------------
    @Test
    public void grabAllGames() {
        //Arrange
        when(mockGameRepo.findAll()).thenReturn(games);

        //Act
        List<Game> testGames = mockGameService.getAllGames();

        //Assert
        assertEquals(3, testGames.size());
        verify(mockGameRepo, times(1)).findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void grabAllGamesExceptionResourceNotFound() {
        //Arrange
        when(mockGameRepo.findAll()).thenReturn(emptyGames);

        //Act
        List<Game> testGames = mockGameService.getAllGames();

        //Assert
        //assertEquals(3, testGames.size());
        verify(mockGameRepo, times(1)).findAll();
    }

    @Test
    public void grabGameById() {
        // Arrange
        //when(mockGameRepo.findById(0)).thenReturn(gameWithIdZero);
        // Act

        // Assert
    }


}


//    //Get ---------------------------------------------------------
//    public List<Game> getAllGames(){
//        List<Game> games;
//
//        games = (List<Game>) gameRepo.findAll();
//        if(games.isEmpty()){
//            throw new ResourceNotFoundException();
//        }
//        return games;
//    }
//
//    public Game getGameById(int id){
//
//        if (id < 0) {
//            throw new InvalidRequestException();
//        }
//
//        Optional<Game> game = gameRepo.findById(id);
//
//        if (!game.isPresent()) {
//            throw new ResourceNotFoundException();
//        }
//        return game.orElse(null);
//    }
//
//    public Game getGameByName(String name){
//
//        if (name == null || name.trim().equals("")){
//            throw new InvalidRequestException();
//        }
//
//        Optional<Game> game = gameRepo.findGameByName(name);
//
//        if (!game.isPresent()) {
//            throw new ResourceNotFoundException();
//        }
//
//        return game.orElse(null);
//    }
