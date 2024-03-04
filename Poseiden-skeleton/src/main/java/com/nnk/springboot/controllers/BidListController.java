package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    @Autowired
    private final BidListService bidListService;

    @Autowired
    private final UserService userService;

    public BidListController(BidListService bidListService, UserService userService) {
        this.bidListService = bidListService;
        this.userService = userService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<BidList> bidLists = bidListService.list();

        model.addAttribute("fullname", fullname);
        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<BidList> bidLists = bidListService.list();

        if (!bidListService.validate(bid, result)) {
            return "bidList/add";
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("bidLists", bidLists);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidList bidList = bidListService.get(id).orElseThrow(() -> new EntityNotFoundException("BidList not found"));

        model.addAttribute("id", id);
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {

        if (!bidListService.validate(bidList, result)) {
            return "bidList/update";
        }

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);

        return "redirect:/bidList/list";
    }
}
