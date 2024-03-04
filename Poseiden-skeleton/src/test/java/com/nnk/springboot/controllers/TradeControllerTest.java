package com.nnk.springboot.controllers;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @InjectMocks
    private TradeController tradeController;

    @Test
    void testHome() {
        String username = "user";
        String fullname = "User Fullname";
        Iterable<Trade> trades = mock(Iterable.class);

        when(principal.getName()).thenReturn(username);
        when(userService.getFullname(username)).thenReturn(fullname);
        when(tradeService.list()).thenReturn(trades);

        String viewName = tradeController.home(model, principal);

        assertEquals("trade/list", viewName);
        verify(model).addAttribute("fullname", fullname);
        verify(model).addAttribute("trades", trades);
    }

    @Test
    void testAddUser() {
        Trade trade = new Trade();

        String viewName = tradeController.addUser(trade);

        assertEquals("trade/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        Trade trade = new Trade();
        when(principal.getName()).thenReturn("user");
        when(userService.getFullname("user")).thenReturn("Full Name");
        when(tradeService.validate(any(Trade.class), any(BindingResult.class))).thenReturn(true);

        String viewName = tradeController.validate(trade, bindingResult, model, principal);

        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        int id = 1;
        Trade trade = new Trade();
        when(tradeService.get(id)).thenReturn(Optional.of(trade));

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("trade/update", viewName);
        verify(model).addAttribute("trade", trade);
    }

    @Test
    void testUpdateTrade() {
        Trade trade = new Trade();
        when(tradeService.validate(any(Trade.class), any(BindingResult.class))).thenReturn(true);

        String viewName = tradeController.updateTrade(1, trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    void testDeleteTrade() {
        int id = 1;

        String viewName = tradeController.deleteTrade(id, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).delete(id);
    }

}