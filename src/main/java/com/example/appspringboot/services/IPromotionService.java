package com.example.appspringboot.services;

import com.example.appspringboot.entities.Promotion;
import java.time.LocalDate;
import java.util.List;

public interface IPromotionService {
    List<Promotion> findAll();
    Promotion findById(Long id);
    void save(Promotion promotion) throws Exception;
    void delete(Long id);
    List<Promotion> search(Double discount, LocalDate start, LocalDate end);
}
