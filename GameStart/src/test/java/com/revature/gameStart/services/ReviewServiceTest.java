package com.revature.gameStart.services;

import com.revature.gameStart.models.*;
import com.revature.gameStart.repositories.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    List<Review> reviews = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Game> games = new ArrayList<>();
    List<Genre> genres = new ArrayList<>();

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        genres.add(new Genre(1,"fps"));
        users.add(new User(1, "ree", "ew", "APww", "Passwww", "aefp@amurica.com", UserRole.BASIC));
        users.add(new User(2, "Apple", "Pie", "AP", "Pass", "ap@amurica.com", UserRole.BASIC));
        games.add(new Game(1,"GTA", genres, "GTA game", 2));
        reviews.add(new Review("work", 5, games.get(0), users.get(0)));

//        Review review = new Review("work", 5, games.get(0), users.get(0));

    }

    @After
    public void tearDown() {
        reviews.clear();
        users.clear();
        games.clear();
        genres.clear();
    }

    //-----------------------------Test------------------------------

    @Test
    public void registerReviewTest() {

        when(reviewRepository.save(reviews.get(0))).thenReturn(reviews.get(0));

        reviewService.registerReview(reviews.get(0));

        verify(reviewRepository, times(1)).save(reviews.get(0));

    }

    @Test
    public void findAllTest() {

        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> testReviews = reviewService.findAllReview();

        assertEquals(1, testReviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    public void findReviewByUserIdandGameId(){
        when(reviewRepository.findReviewByUserAndGame(users.get(0).getId(), games.get(0).getId())).thenReturn(Optional.ofNullable(reviews.get(0)));

        Review testReview = reviewService.getReviewByUserAndGameId(users.get(0).getId(), games.get(0).getId());

        assertEquals(testReview,reviews.get(0));

    }

    @Test
    public void findReviewByGameId(){
        when(reviewRepository.findById(games.get(0).getId())).thenReturn(Optional.ofNullable(reviews.get(0)));

        Review testReview = reviewService.getReviewsByGameId(games.get(0).getId());

        assertEquals(reviews.get(0),testReview);

    }

    @Test
    public void findReviewByUserId() {
        when(reviewRepository.findById(users.get(0).getId())).thenReturn(Optional.ofNullable(reviews.get(0)));

        Review testReview = reviewService.getReviewsByUserId(users.get(0).getId());

        assertEquals(reviews.get(0), testReview);

    }

    @Test
    public void updateReviewTest(){
        Review newReview = new Review("hiugyuvovy",0,games.get(0),users.get(0));
        //when(reviewRepository.updateReview(newReview.getUser().getId(),newReview.getGame().getId(),newReview.getDescription(), newReview.getScore())).thenReturn(reviews.get(0));
        when(reviewRepository.save(newReview)).thenReturn(newReview);
        reviewService.updateReview(newReview);

        //assertEquals(reviews.get(0),newReview);
        verify(reviewRepository, times(1)).save(newReview);

    }

    @Test
    public void updateReviewDescriptionTest(){
        when(reviewRepository.updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION")).thenReturn(reviews.get(0));

        reviewService.updateReviewDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");

        verify(reviewRepository, times(1)).updateDescription(users.get(0).getId(),games.get(0).getId(),"CHANGED DESCRIPTION");
    }

    @Test
    public void updateReviewScoreTest(){
        when(reviewRepository.updateScore(users.get(0).getId(),games.get(0).getId(),1)).thenReturn(reviews.get(0));

        reviewService.updateReviewScore(users.get(0).getId(),games.get(0).getId(),1);

        verify(reviewRepository, times(1)).updateScore(users.get(0).getId(),games.get(0).getId(),1);
    }

    @Test
    public void deleteReview(){
        when(reviewRepository.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId())).thenReturn(reviews.get(0));

        reviewService.deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());

        verify(reviewRepository, times(1)).deleteReviewByUserIdAndGameId(reviews.get(0).getUser().getId(),reviews.get(0).getGame().getId());

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
