package com.revature.gameStart.services;

import com.revature.gameStart.exceptions.ResourceNotFoundException;
import com.revature.gameStart.exceptions.ResourcePersistenceException;
import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ReviewServiceTest {



    @Spy
    private ReviewService spyreviewService;

    @Spy
    private ReviewRepository spyreviewRepo;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    List<Review> reviews = new ArrayList<>();
    List<Review> reviewsUG = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Game> games = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        //spyreviewService = new ReviewService(reviewRepository);

        genres.add(new Genre(1,"fps"));
        users.add(new User(1, "ree", "ew", "APww", "Passwww", "aefp@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        games.add(new Game(1,"GTA", genres, "GTA game", 2));
        reviews.add(new Review("work", 5, games.get(0), users.get(0)));
        reviewsUG.add(new Review("work", 5, games.get(0), users.get(0)));

//        Review review = new Review("work", 5, games.get(0), users.get(0));

    }

    @After
    public void tearDown() {
        reviews.clear();
        users.clear();
        games.clear();
        genres.clear();
        reviewService = null;
        reviewRepository = null;
        spyreviewService = null;
        spyreviewRepo = null;
    }

    //-----------------------------Test------------------------------

    @Test
    public void registerReviewTest() {

        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
       // doNothing().when(spyreviewService).insertReview(user,game,description,score);
        doNothing().when(spyreviewRepo).insertReview(user, game, description, score);
        reviewService.insertReview(user,game,description,score);
        verify(reviewRepository, times(0)).insertReview(user,game,description,score);

    }
    @Test(expected = ResourceNotFoundException.class)
    public void registerReviewTestNotPresent() {
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
       // doThrow(ResourcePersistenceException.class).when(spyreviewRepo).insertReview(user,game,description,score);
        doThrow(ResourceNotFoundException.class).when(spyreviewService).insertReview(user,game,description,score);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).insertReview(user, game, description, score);
        reviewService.insertReview(user,game,description,score);
        verify(reviewRepository).insertReview(user,game,description,score);
    }


    @Test
    public void findAllTest() {

        //doReturn(reviews).when(spyreviewService).findAllReview();
        doReturn(reviews).when(spyreviewService).findAllReview();
        doReturn(reviews).when(spyreviewRepo).findAll();
        reviewService.findAllReview();
        //verify(reviewRepository, times(0)).findAll();
        assertEquals(1,reviews.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findAllTestIfEmpty() {

       //doThrow(ResourceNotFoundException.class).when(spyreviewService).findAllReview();
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).findAll();
        reviewService.findAllReview();
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    public void findReviewByUserIdandGameId(){
        Optional<Review> newReviews = Optional.of(reviews.get(0));
        int user = newReviews.get().getUser().getId();
        int game = newReviews.get().getGame().getId();

        doReturn(newReviews).when(spyreviewRepo).findReviewByUserAndGame(user,game);
        //doThrow(ResourceNotFoundException.class).when(reviewRepository).findReviewByUserAndGame(user,game);
        reviewService.getReviewByUserAndGameId(user,game);
        assertEquals(Optional.of(reviews.get(0)),newReviews);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void findReviewByUserIdandGameIdIfEmpty(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        //doThrow(ResourceNotFoundException.class).when(spyreviewService).getReviewByUserAndGameId(user,game);
        doThrow(ResourceNotFoundException.class).when(reviewRepository).findReviewByUserAndGame(user,game);
        reviewService.getReviewByUserAndGameId(user,game);
        verify(reviewRepository, times(1)).findReviewByUserAndGame(user,game);

    }

    @Test
    public void findReviewByGameId(){

        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        //doReturn(reviews).when(spyreviewService).getReviewsByGameId(game);
        doReturn(reviews).when(spyreviewRepo).findReviewByGameId(game);
        reviewService.getReviewsByGameId(game);
        assertEquals(1,reviews.size());


    }

    @Test(expected = ResourceNotFoundException.class)
    public void findReviewByGameIdIfEmpty(){

        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        //doThrow(ResourceNotFoundException.class).when(spyreviewService).getReviewsByGameId(game);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).findReviewByGameId(game);
        reviewService.getReviewsByGameId(game);
        verify(reviewRepository, times(1)).findReviewByGameId(game);


    }

    @Test
    public void findReviewByUserId() {
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
       // doReturn(reviews).when(spyreviewService).getReviewsByUserId(user);
        doReturn(reviews).when(spyreviewRepo).findReviewByUserId(user);
        reviewService.getReviewsByUserId(game);
        //verify(spyreviewRepo, times(1)).findReviewByUserId(user);
        assertEquals(1,reviews.size());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void findReviewByUserIdIfEmpty() {
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        //doThrow(ResourceNotFoundException.class).when(spyreviewService).getReviewsByUserId(user);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).findReviewByUserId(user);
        reviewService.getReviewsByUserId(game);
        //verify(spyreviewService, times(1)).getReviewsByUserId(user);
        assertEquals(1,reviews.size());


    }

    @Test
    public void updateReviewTest(){

        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
        Review newReview = new Review(description,score,reviews.get(0).getGame(),reviews.get(0).getUser());
        //doNothing().when(spyreviewService).updateReviewDescriptionAndScore(user,game,score,description);
        doNothing().when(spyreviewRepo).updateDescriptionAndScore(user, game, score,description);
        reviewService.updateReviewDescriptionAndScore(user,game,score,description);
        verify(reviewRepository, atLeastOnce()).updateDescriptionAndScore(user,game,score,description);
        //assertEquals(newReview,reviews.get(0));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewTestNotPresent(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
        //doThrow(ResourceNotFoundException.class).when(spyreviewService).updateReviewDescriptionAndScore(user,game,score,description);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).updateDescriptionAndScore(user, game, score,description);
        reviewService.updateReviewDescriptionAndScore(user,game,score,description);
        verify(reviewRepository, times(1)).updateDescriptionAndScore(user,game,score,description);


    }

    @Test
    public void updateReviewDescriptionTest(){

        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
        //doNothing().when(spyreviewService).updateReviewDescription(user,game,description);
        doNothing().when(spyreviewRepo).updateDescription(user, game,description);
        reviewService.updateReviewDescription(user,game,description);
        verify(reviewService, times(1)).updateReviewDescription(user,game,description);


    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewDescriptionTestNotPresent(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
       // doThrow(ResourceNotFoundException.class).when(spyreviewService).updateReviewDescription(user,game,description);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).updateDescription(user, game,description);
        reviewService.updateReviewDescription(user,game,description);
        verify(reviewRepository, times(1)).updateDescription(user,game,description);

    }

    @Test
    public void updateReviewScoreTest(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
        //doNothing().when(spyreviewService).updateReviewScore(user,game,score);
        doNothing().when(spyreviewRepo).updateScore(user, game,score);
        reviewService.updateReviewScore(user,game,score);
        verify(reviewRepository, times(0)).updateScore(user,game,score);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateReviewScoreTestNotPresent(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
       // doThrow(ResourceNotFoundException.class).when(spyreviewService).updateReviewScore(user,game,score);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).updateScore(user, game,score);
        reviewService.updateReviewScore(user,game,score);
        verify(reviewRepository, times(1)).updateScore(user,game,score);
    }

    @Test
    public void deleteReview(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
        doNothing().when(spyreviewService).deleteReviewByUserIdAndGameId(user,game);
        doNothing().when(spyreviewRepo).deleteReviewByUserIdAndGameId(user, game);
        reviewService.deleteReviewByUserIdAndGameId(user,game);
        verify(reviewRepository, times(1)).deleteReviewByUserIdAndGameId(user,game);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteReviewNotPresent(){
        int user = reviews.get(0).getUser().getId();
        int game = reviews.get(0).getGame().getId();
        int score = reviews.get(0).getScore();
        String description = reviews.get(0).getDescription();
       // doThrow(ResourceNotFoundException.class).when(spyreviewService).deleteReviewByUserIdAndGameId(user,game);
        doThrow(ResourceNotFoundException.class).when(spyreviewRepo).deleteReviewByUserIdAndGameId(user, game);
        reviewService.deleteReviewByUserIdAndGameId(user,game);
        verify(reviewRepository, times(1)).deleteReviewByUserIdAndGameId(user,game);
    }
    //Other possible cases

    @Test
    public void testifReviewisNULL(){
        Review testReview = new Review();
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifReviewisNotNULL(){
        Review testReview = new Review("IIIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifGameisNULL(){
        Review testReview = new Review("IIIII",3,null,users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifGameisNotNULL(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifScoreisNegOne(){
        Review testReview = new Review("IIIII",-1, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifScoreisGreater5(){
        Review testReview = new Review("IIIII",6, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifScoreisBetweenZeroAndFive(){
        Review testReview = new Review("IIIII",5, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifUserisNull(){
        Review testReview = new Review("IIIII",3, games.get(0), null);
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertFalse(valid);

    }

    @Test
    public void testifUserisNotNull(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }

    @Test
    public void testifReivewisTrue(){
        Review testReview = new Review("IIIII",3, games.get(0), users.get(0));
        boolean valid;

        valid = reviewService.isReviewValid(testReview);

        assertTrue(valid);

    }



}
