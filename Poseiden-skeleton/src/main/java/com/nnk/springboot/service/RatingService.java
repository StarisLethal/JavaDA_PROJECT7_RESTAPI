package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;


@Service
public class RatingService {

    @Autowired
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Iterable<Rating> list() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> get(Integer id) {
        return ratingRepository.findById(id);
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public boolean validate(Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            return false;
        } else {
            ratingRepository.save(rating);
            return true;
        }
    }

    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }
}
