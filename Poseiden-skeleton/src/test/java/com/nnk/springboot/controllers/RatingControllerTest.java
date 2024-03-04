package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RatingControllerTest {


    @Mock
    private RatingService ratingService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @InjectMocks
    private RatingController ratingController;

    @Test
    void testHome() {
        String username = "user";
        String fullname = "User Fullname";
        Iterable<Rating> ratings = mock(Iterable.class);

        when(principal.getName()).thenReturn(username);
        when(userService.getFullname(username)).thenReturn(fullname);
        when(ratingService.list()).thenReturn(ratings);

        String viewName = ratingController.home(model, principal);

        assertEquals("rating/list", viewName);
        verify(model).addAttribute("fullname", fullname);
        verify(model).addAttribute("ratings", ratings);
    }

    @Test
    void testAddRatingForm() {
        Rating rating = new Rating();

        String viewName = ratingController.addRatingForm(rating);

        assertEquals("rating/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        Rating rating = new Rating();
        when(principal.getName()).thenReturn("user");
        when(userService.getFullname("user")).thenReturn("Full Name");
        when(ratingService.validate(any(Rating.class), any(BindingResult.class))).thenReturn(true);

        String viewName = ratingController.validate(rating, bindingResult, model, principal);

        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        int id = 1;
        Rating rating = new Rating();
        when(ratingService.get(id)).thenReturn(Optional.of(rating));

        String viewName = ratingController.showUpdateForm(id, model);

        assertEquals("rating/update", viewName);
        verify(model).addAttribute("rating", rating);
    }

    @Test
    void testUpdateRating() {
        Rating rating = new Rating();
        when(ratingService.validate(any(Rating.class), any(BindingResult.class))).thenReturn(true);

        String viewName = ratingController.updateRating(1, rating, bindingResult, model);

        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    void testDeleteRating() {
        int id = 1;

        String viewName = ratingController.deleteRating(id, model);

        assertEquals("redirect:/rating/list", viewName);
        verify(ratingService).delete(id);
    }
}