package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {

    @Autowired
    public final RuleNameService ruleNameService;
    @Autowired
    public final UserService userService;

    public RuleNameController(RuleNameService ruleNameService, UserService userService) {
        this.ruleNameService = ruleNameService;
        this.userService = userService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);

        Iterable<RuleName> ruleNames = ruleNameService.list();

        model.addAttribute("fullname", fullname);
        model.addAttribute("ruleNames", ruleNames);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }


    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);

        if (!ruleNameService.validate(ruleName, result)) {
            return "ruleName/update";
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("ruleName", ruleNameService.list());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleNameService.get(id).orElseThrow(() -> new EntityNotFoundException("Rating not found"));


        model.addAttribute("id", id);
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {

        if (!ruleNameService.validate(ruleName, result)) {
            return "rating/list";
        }

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        ruleNameService.delete(id);

        return "redirect:/ruleName/list";
    }
}
