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

import static org.junit.Assert.assertEquals;
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


}
