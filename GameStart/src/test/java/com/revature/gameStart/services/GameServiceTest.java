
package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.InvalidRequestException;
import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.GameRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    Game gameWithNameApple;
    Optional<Game> gamewithNameAppleOptional;

    List<Game> nullGame;




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

        gameWithIdZeroOptional = Optional.of(gameWithIdZero);

        gameWithNameApple = new Game("Apple", genres, "Description", 6, devs, pubs, plats);
        gameWithNameApple.setId(5);

        gamewithNameAppleOptional = Optional.of(gameWithNameApple);

        nullGame = null;
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
    public void grabTop10Games() {
        when(mockGameRepo.findTop10RatedGames()).thenReturn(Optional.ofNullable(games));

        List<Game> testGames = mockGameService.getTop10Games();

        verify(mockGameRepo, times(1)).findTop10RatedGames();
    }

    @Test
    public void grabGameById() {
        // Arrange
        when(mockGameRepo.findById(1)).thenReturn(gameWithIdZeroOptional);
        // Act
        Game testGame = mockGameService.getGameById(1);
        // Assert

        verify(mockGameRepo, times(1)).findById(1);
    }

    @Test(expected = InvalidRequestException.class)
    public void grabGameByIdInvalidRequestException() {
        // Arrange
        when(mockGameRepo.findById(-1)).thenReturn(gameWithIdZeroOptional);
        // Act
        Game testGame = mockGameService.getGameById(-1);
        // Assert

        verify(mockGameRepo, times(0)).findById(-1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void grabGameByIdResourceNotFoundException() {
        // Arrange
        when(mockGameRepo.findById(5)).thenReturn(Optional.empty());
        // Act
        Game testGame = mockGameService.getGameById(5);
        // Assert

        verify(mockGameRepo, times(1)).findById(5);
    }



    @Test @Ignore
    public void getGameByName() {
        // Arrange
        when(mockGameRepo.findGameByName("Apple")).thenReturn(gamewithNameAppleOptional);
        // Act
        Game testGame = mockGameService.getGameByName("Apple");
        // Assert

        verify(mockGameRepo, times(1)).findGameByName("Apple");
    }

    @Test(expected = InvalidRequestException.class)
    public void getGameByNameInvalidRequestExceptionNull() {
        // Arrange
        when(mockGameRepo.findGameByName(null)).thenReturn(Optional.empty());
        // Act
        Game testGame = mockGameService.getGameByName(null);
        // Assert

        verify(mockGameRepo, times(0)).findGameByName(null);
    }
    @Test(expected = InvalidRequestException.class)
    public void getGameByNameInvalidRequestExceptionEmptyString() {
        // Arrange
        when(mockGameRepo.findGameByName("")).thenReturn(Optional.empty());
        // Act
        Game testGame = mockGameService.getGameByName("");
        // Assert

        verify(mockGameRepo, times(0)).findGameByName("");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getGameByNameResourceNotFoundException() {
        // Arrange
        when(mockGameRepo.findGameByName("Anything")).thenReturn(Optional.empty());
        // Act
        Game testGame = mockGameService.getGameByName("Anything");
        // Assert

        verify(mockGameRepo, times(1)).findGameByName("Anything");
    }

    @Test
    public void insertGames() {
        // Arrange
        when(mockGameRepo.save(games.get(0))).thenReturn(games.get(0));
        when(mockGameRepo.save(games.get(1))).thenReturn(games.get(1));
        when(mockGameRepo.save(games.get(2))).thenReturn(games.get(2));
        // Act
        mockGameService.insertGame(games);
        // Assert
        verify(mockGameRepo, times(1)).save(games.get(0));
        verify(mockGameRepo, times(1)).save(games.get(1));
        verify(mockGameRepo, times(1)).save(games.get(2));
    }

    @Test(expected = InvalidRequestException.class)
    public void insertGamesInvalidRequestExceptionNull () {
        // Arrange

        // Act
        mockGameService.insertGame(nullGame);
        // Assert
        verify(mockGameRepo, times(0)).save(games.get(0));
    }
    @Test(expected = InvalidRequestException.class)
    public void insertGamesInvalidRequestExceptionEmpty () {
        // Arrange

        // Act
        mockGameService.insertGame(emptyGames);
        // Assert
        verify(mockGameRepo, times(0)).save(games.get(0));
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
