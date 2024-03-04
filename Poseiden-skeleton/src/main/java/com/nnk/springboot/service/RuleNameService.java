package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public Optional<RuleName> get(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public Iterable<RuleName> list() {
        return ruleNameRepository.findAll();
    }

    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public boolean validate(@Valid RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            return false;
        } else {
            ruleNameRepository.save(ruleName);
            return true;
        }
    }

    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
