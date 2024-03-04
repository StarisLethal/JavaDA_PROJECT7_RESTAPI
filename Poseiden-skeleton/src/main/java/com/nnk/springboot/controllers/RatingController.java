package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class RatingController {

    @Autowired
    private final RatingService ratingService;

    @Autowired
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<Rating> ratings = ratingService.list();

        model.addAttribute("fullname", fullname);
        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<Rating> ratings = ratingService.list();

        if (!ratingService.validate(rating, result)) {
            return "rating/add";
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("ratings", ratings);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Rating rating = ratingService.get(id).orElseThrow(() -> new EntityNotFoundException("Rating not found"));

        model.addAttribute("id", id);
        model.addAttribute("rating", rating);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {

        if (!ratingService.validate(rating, result)) {
            return "rating/update";
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {

        ratingService.delete(id);

        return "redirect:/rating/list";
    }
}
