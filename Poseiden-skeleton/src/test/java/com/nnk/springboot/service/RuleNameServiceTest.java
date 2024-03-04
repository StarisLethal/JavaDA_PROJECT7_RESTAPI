package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;
    @InjectMocks
    private RuleNameService ruleNameService;
    @Mock
    private BindingResult bindingResult;


    @Test
    void list() {
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(new RuleName());
        ruleNames.add(new RuleName());

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        Iterable<RuleName> result = ruleNameService.list();

        verify(ruleNameRepository).findAll();
        assertEquals(ruleNames, result);
    }

    @Test
    void get() {
        Optional<RuleName> ruleName = Optional.of(new RuleName());
        Integer idTest = 1;

        when(ruleNameRepository.findById(idTest)).thenReturn(ruleName);

        Optional<RuleName> result = ruleNameService.get(idTest);

        verify(ruleNameRepository).findById(idTest);
        assertEquals(ruleName, result);
    }

    @Test
    void save() {
        Integer idTest = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(idTest);

        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleName result = ruleNameService.save(ruleName);

        verify(ruleNameRepository).save(ruleName);
        assertEquals(ruleName, result);
    }

    @Test
    void validateTrue() {
        RuleName ruleName = new RuleName();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        Boolean result = ruleNameService.validate(ruleName, bindingResult);

        verify(ruleNameRepository).save(ruleName);
        assertTrue(result);
    }

    @Test
    void validateFalse() {
        RuleName ruleName = new RuleName();

        when(bindingResult.hasErrors()).thenReturn(true);

        boolean result = ruleNameService.validate(ruleName, bindingResult);

        assertFalse(result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        ruleNameService.delete(idTest);

        verify(ruleNameRepository).deleteById(idTest);
    }
}
