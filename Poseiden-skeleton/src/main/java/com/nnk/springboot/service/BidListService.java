package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    public final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public Iterable<BidList> list() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> get(Integer id) {
        return bidListRepository.findById(id);
    }

    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public boolean validate(BidList bidList, BindingResult result) {
        if (result.hasErrors()) {
            return false;
        } else {
            bidListRepository.save(bidList);
            return true;
        }
    }

    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
}
