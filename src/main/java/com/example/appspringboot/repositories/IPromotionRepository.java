package com.example.appspringboot.repositories;

import com.example.appspringboot.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface IPromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("SELECT p FROM Promotion p WHERE " +
            "(:discount IS NULL OR p.discountRate = :discount) AND " +
            "(:start IS NULL OR p.startDate >= :start) AND " +
            "(:end IS NULL OR p.endDate <= :end)")
    List<Promotion> searchPromotions(
            @Param("discount") Double discount,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
