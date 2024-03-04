package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    public final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Iterable<Trade> list() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> get(Integer id) {
        return tradeRepository.findById(id);
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public boolean validate(Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return false;
        } else {
            tradeRepository.save(trade);
            return true;
        }
    }

    public void delete(Integer id) {
        tradeRepository.deleteById(id);
    }
}
