package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @InjectMocks
    private RuleNameController ruleNameController;

    @Test
    void testHome() {
        String username = "user";
        String fullname = "User Fullname";
        Iterable<RuleName> ruleNames = mock(Iterable.class);

        when(principal.getName()).thenReturn(username);
        when(userService.getFullname(username)).thenReturn(fullname);
        when(ruleNameService.list()).thenReturn(ruleNames);

        String viewName = ruleNameController.home(model, principal);

        assertEquals("ruleName/list", viewName);
        verify(model).addAttribute("fullname", fullname);
        verify(model).addAttribute("ruleNames", ruleNames);
    }

    @Test
    void testAddRuleForm() {
        RuleName ruleName = new RuleName();

        String viewName = ruleNameController.addRuleForm(ruleName);

        assertEquals("ruleName/add", viewName);
    }

    @Test
    void testValidateSuccess() {
        RuleName ruleName = new RuleName();
        when(principal.getName()).thenReturn("user");
        when(userService.getFullname("user")).thenReturn("Full Name");
        when(ruleNameService.validate(any(RuleName.class), any(BindingResult.class))).thenReturn(true);

        String viewName = ruleNameController.validate(ruleName, bindingResult, model, principal);

        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    void testShowUpdateForm() {
        int id = 1;
        RuleName ruleName = new RuleName();
        when(ruleNameService.get(id)).thenReturn(Optional.of(ruleName));

        String viewName = ruleNameController.showUpdateForm(id, model);

        assertEquals("ruleName/update", viewName);
        verify(model).addAttribute("ruleName", ruleName);
    }

    @Test
    void testUpdateRuleName() {
        RuleName ruleName = new RuleName();
        when(ruleNameService.validate(any(RuleName.class), any(BindingResult.class))).thenReturn(true);

        String viewName = ruleNameController.updateRuleName(1, ruleName, bindingResult, model);

        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    void testDeleteRuleName() {
        int id = 1;

        String viewName = ruleNameController.deleteRuleName(id, model);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(ruleNameService).delete(id);
    }

}