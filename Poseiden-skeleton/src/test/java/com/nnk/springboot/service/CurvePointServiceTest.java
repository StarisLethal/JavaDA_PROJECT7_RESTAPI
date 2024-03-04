package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;
    @InjectMocks
    private CurvePointService curvePointService;
    @Mock
    private BindingResult bindingResult;


    @Test
    void list() {
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(new CurvePoint());
        curvePoints.add(new CurvePoint());

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        Iterable<CurvePoint> result = curvePointService.list();

        verify(curvePointRepository).findAll();
        assertEquals(curvePoints, result);
    }

    @Test
    void get() {
        Optional<CurvePoint> curvePoint = Optional.of(new CurvePoint());
        Integer idTest = 1;

        when(curvePointRepository.findById(idTest)).thenReturn(curvePoint);

        Optional<CurvePoint> result = curvePointService.get(idTest);

        verify(curvePointRepository).findById(idTest);
        assertEquals(curvePoint, result);
    }

    @Test
    void save() {
        Integer idTest = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(idTest);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint result = curvePointService.save(curvePoint);

        verify(curvePointRepository).save(curvePoint);
        assertEquals(curvePoint, result);
    }

    @Test
    void validateTrue() {
        CurvePoint curvePoint = new CurvePoint();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        Boolean result = curvePointService.validate(curvePoint, bindingResult);

        verify(curvePointRepository).save(curvePoint);
        assertTrue(result);
    }

    @Test
    void validateFalse() {
        CurvePoint curvePoint = new CurvePoint();

        when(bindingResult.hasErrors()).thenReturn(true);

        boolean result = curvePointService.validate(curvePoint, bindingResult);

        assertFalse(result);
    }

    @Test
    void delete() {
        Integer idTest = 1;

        curvePointService.delete(idTest);

        verify(curvePointRepository).deleteById(idTest);
    }
}
