package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveController {

    @Autowired
    public final CurvePointService curvePointService;
    @Autowired
    public final UserService userService;

    public CurveController(CurvePointService curvePointService, UserService userService) {
        this.curvePointService = curvePointService;
        this.userService = userService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<CurvePoint> curvePoints = curvePointService.list();


        model.addAttribute("fullname", fullname);
        model.addAttribute("curvePoints", curvePoints);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model, Principal principal) {

        String username = principal.getName();
        String fullname = userService.getFullname(username);
        Iterable<CurvePoint> curvePoints = curvePointService.list();

        curvePointService.creationDate(curvePoint);

        if (!curvePointService.validate(curvePoint, result)) {
            return "curvePoint/add";
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("curvePoints", curvePoints);

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurvePoint curvePoint = curvePointService.get(id).orElseThrow(() -> new EntityNotFoundException("CurvePoint not found"));

        model.addAttribute("id", id);
        model.addAttribute("curvePoint", curvePoint);

        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {

        if (!curvePointService.validate(curvePoint, result)) {
            return "curvePoint/update";
        }

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        curvePointService.delete(id);

        return "redirect:/curvePoint/list";
    }
}
