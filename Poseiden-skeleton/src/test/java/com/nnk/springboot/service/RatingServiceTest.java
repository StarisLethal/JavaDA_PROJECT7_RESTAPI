package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingService ratingService;
    @Mock
    private BindingResult bindingResult;


    @Test
    void list() {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating());
        ratings.add(new Rating());

        when(ratingRepository.findAll()).thenReturn(ratings);

        Iterable<Rating> result = ratingService.list();

        verify(ratingRepository).findAll();
        assertEquals(ratings, result);
    }

    @Test
    void get() {
        Optional<Rating> rating = Optional.of(new Rating());
        Integer idTest = 1;

        when(ratingRepository.findById(idTest)).thenReturn(rating);

        Optional<Rating> result = ratingService.get(idTest);

        verify(ratingRepository).findById(idTest);
        assertEquals(rating, result);
    }

    @Test
    void save() {
        Integer idTest = 1;
        Rating rating = new Rating();
        rating.setId(idTest);

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.save(rating);

        verify(ratingRepository).save(rating);
        assertEquals(rating, result);
    }

    @Test
    void validateTrue() {
        Rating rating = new Rating();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(ratingRepository.save(rating)).thenReturn(rating);

        Boolean result = ratingService.validate(rating, bindingResult);

        verify(ratingRepository).save(rating);
        assertTrue(result);
    }

    @Test
    void validateFalse() {
        Rating rating = new Rating();

        when(bindingResult.hasErrors()).thenReturn(true);

        boolean result = ratingService.validate(rating, bindingResult);

        assertFalse(result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        ratingService.delete(idTest);

        verify(ratingRepository).deleteById(idTest);
    }
}
