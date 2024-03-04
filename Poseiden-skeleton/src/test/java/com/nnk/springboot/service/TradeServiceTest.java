package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeService tradeService;
    @Mock
    private BindingResult bindingResult;


    @Test
    void list() {
        List<Trade> trades = new ArrayList<>();
        trades.add(new Trade());
        trades.add(new Trade());

        when(tradeRepository.findAll()).thenReturn(trades);

        Iterable<Trade> result = tradeService.list();

        verify(tradeRepository).findAll();
        assertEquals(trades, result);
    }

    @Test
    void get() {
        Optional<Trade> trade = Optional.of(new Trade());
        Integer idTest = 1;

        when(tradeRepository.findById(idTest)).thenReturn(trade);

        Optional<Trade> result = tradeService.get(idTest);

        verify(tradeRepository).findById(idTest);
        assertEquals(trade, result);
    }

    @Test
    void save() {
        Integer idTest = 1;
        Trade trade = new Trade();
        trade.setId(idTest);

        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade result = tradeService.save(trade);

        verify(tradeRepository).save(trade);
        assertEquals(trade, result);
    }

    @Test
    void validateTrue() {
        Trade trade = new Trade();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(tradeRepository.save(trade)).thenReturn(trade);

        Boolean result = tradeService.validate(trade, bindingResult);

        verify(tradeRepository).save(trade);
        assertTrue(result);
    }

    @Test
    void validateFalse() {
        Trade trade = new Trade();

        when(bindingResult.hasErrors()).thenReturn(true);

        boolean result = tradeService.validate(trade, bindingResult);

        assertFalse(result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        tradeService.delete(idTest);

        verify(tradeRepository).deleteById(idTest);
    }
}
