package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @InjectMocks
    private BidListController bidListController;

    @Test
    void testHome() {
        String username = "user";
        String fullname = "User Fullname";
        Iterable<BidList> bidLists = mock(Iterable.class);

        when(principal.getName()).thenReturn(username);
        when(userService.getFullname(username)).thenReturn(fullname);
        when(bidListService.list()).thenReturn(bidLists);

        String viewName = bidListController.home(model, principal);

        assertEquals("bidList/list", viewName);
        verify(model).addAttribute("fullname", fullname);
        verify(model).addAttribute("bidLists", bidLists);
    }

    @Test
    void testAddBidForm() {
        BidList bid = new BidList();

        String viewName = bidListController.addBidForm(bid);

        assertEquals("bidList/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        BidList bid = new BidList();
        when(principal.getName()).thenReturn("user");
        when(userService.getFullname("user")).thenReturn("Full Name");
        when(bidListService.validate(any(BidList.class), any(BindingResult.class))).thenReturn(true);

        String viewName = bidListController.validate(bid, bindingResult, model, principal);

        verify(model).addAttribute("fullname", "Full Name");
        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        int id = 1;
        BidList bidList = new BidList();
        when(bidListService.get(id)).thenReturn(Optional.of(bidList));

        String viewName = bidListController.showUpdateForm(id, model);

        assertEquals("bidList/update", viewName);
        verify(model).addAttribute("bidList", bidList);
    }

    @Test
    void testUpdateBid() {
        BidList bidList = new BidList();
        when(bidListService.validate(any(BidList.class), any(BindingResult.class))).thenReturn(true);

        String viewName = bidListController.updateBid(1, bidList, bindingResult, model);

        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    void testDeleteBid() {
        int id = 1;

        String viewName = bidListController.deleteBid(id, model);

        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).delete(id);
    }
}