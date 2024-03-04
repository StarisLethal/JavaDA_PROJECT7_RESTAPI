package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidListService bidListService;
    @Mock
    private BindingResult bindingResult;


    @Test
    void list() {
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(new BidList());
        bidLists.add(new BidList());

        when(bidListRepository.findAll()).thenReturn(bidLists);

        Iterable<BidList> result = bidListService.list();

        verify(bidListRepository).findAll();
        assertEquals(bidLists, result);
    }

    @Test
    void get() {
        Optional<BidList> bidList = Optional.of(new BidList());
        Integer idTest = 1;

        when(bidListRepository.findById(idTest)).thenReturn(bidList);

        Optional<BidList> result = bidListService.get(idTest);

        verify(bidListRepository).findById(idTest);
        assertEquals(bidList, result);
    }

    @Test
    void save() {
        Integer idTest = 1;
        BidList bidList = new BidList();
        bidList.setId(idTest);

        when(bidListRepository.save(bidList)).thenReturn(bidList);

        BidList result = bidListService.save(bidList);

        verify(bidListRepository).save(bidList);
        assertEquals(bidList, result);
    }

    @Test
    void validateTrue() {
        BidList bidList = new BidList();
        bidList.setId(1);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(10d);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(bidListRepository.save(bidList)).thenReturn(bidList);

        Boolean result = bidListService.validate(bidList, bindingResult);

        verify(bidListRepository).save(bidList);
        assertTrue(result);
    }

    @Test
    void validateFalse() {
        BidList bidList = new BidList();
        bidList.setId(1);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(10d);

        when(bindingResult.hasErrors()).thenReturn(true);

        boolean result = bidListService.validate(bidList, bindingResult);

        assertFalse(result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        bidListService.delete(idTest);

        verify(bidListRepository).deleteById(idTest);
    }
}