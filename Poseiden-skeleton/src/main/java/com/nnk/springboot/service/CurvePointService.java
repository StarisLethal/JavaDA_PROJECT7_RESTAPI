package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    public final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public Iterable<CurvePoint> list() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> get(Integer id) {
        return curvePointRepository.findById(id);
    }

    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public boolean validate(CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return false;
        } else {
            curvePointRepository.save(curvePoint);
            return true;
        }
    }

    public void creationDate(CurvePoint curvePoint) {
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
    }

    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
