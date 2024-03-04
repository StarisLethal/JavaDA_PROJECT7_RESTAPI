package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
class CurveControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @InjectMocks
    private CurveController curveController;

    @Test
    void testHome() {
        String username = "user";
        String fullname = "User Fullname";
        Iterable<CurvePoint> curvePoints = mock(Iterable.class);

        when(principal.getName()).thenReturn(username);
        when(userService.getFullname(username)).thenReturn(fullname);
        when(curvePointService.list()).thenReturn(curvePoints);

        String viewName = curveController.home(model, principal);

        assertEquals("curvePoint/list", viewName);
        verify(model).addAttribute("fullname", fullname);
        verify(model).addAttribute("curvePoints", curvePoints);
    }

    @Test
    void testAddCurvePointForm() {
        String viewName = curveController.addBidForm(new CurvePoint());

        assertEquals("curvePoint/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        CurvePoint curvePoint = new CurvePoint();
        when(principal.getName()).thenReturn("user");
        when(userService.getFullname("user")).thenReturn("Full Name");
        when(curvePointService.validate(any(CurvePoint.class), any(BindingResult.class))).thenReturn(true);

        String viewName = curveController.validate(curvePoint, bindingResult, model, principal);

        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.get(1)).thenReturn(Optional.of(curvePoint));

        String viewName = curveController.showUpdateForm(1, model);

        assertEquals("curvePoint/update", viewName);
        verify(model).addAttribute("curvePoint", curvePoint);
    }

    @Test
    void testUpdateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.validate(any(CurvePoint.class), any(BindingResult.class))).thenReturn(true);

        String viewName = curveController.updateBid(1, curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    void testDeleteCurvePoint() {
        String viewName = curveController.deleteBid(1, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).delete(1);
    }
}