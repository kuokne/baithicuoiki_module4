package com.example.appspringboot.services;

import com.example.appspringboot.entities.Promotion;
import com.example.appspringboot.repositories.IPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
public class PromotionService implements IPromotionService {
    @Autowired
    private IPromotionRepository repository;

    @Override
    public List<Promotion> findAll() { return repository.findAll(); }

    @Override
    public void save(Promotion p) throws Exception {
        if (!p.getEndDate().isAfter(p.getStartDate())) {
            throw new Exception("Ngày kết thúc phải sau ngày bắt đầu ít nhất 1 ngày");
        }
        repository.save(p);
    }

    @Override
    public void delete(Long id) { repository.deleteById(id); }

    @Override
    public List<Promotion> search(Double discount, LocalDate start, LocalDate end) {
        return repository.searchPromotions(discount, start, end);
    }

    @Override
    public Promotion findById(Long id) { return repository.findById(id).orElse(null); }
}
