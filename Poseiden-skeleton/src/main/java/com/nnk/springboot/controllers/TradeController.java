package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {

    @Autowired
    public final TradeService tradeService;
    @Autowired
    public final UserService userService;

    public TradeController(TradeService tradeService, UserService userService) {
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<Trade> trades = tradeService.list();

        model.addAttribute("fullname", fullname);
        model.addAttribute("trades", trades);

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<Trade> trades = tradeService.list();

        if (!tradeService.validate(trade, result)) {
            return "trade/update";
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("trades", trades);

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        Trade trade = tradeService.get(id).orElseThrow(() -> new EntityNotFoundException("Trade not found"));

        model.addAttribute("id", id);
        model.addAttribute("trade", trade);

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {

        if (!tradeService.validate(trade, result)) {
            return "trade/list";
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        tradeService.delete(id);

        return "redirect:/trade/list";
    }
}
