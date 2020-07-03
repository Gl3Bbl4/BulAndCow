package com.game.jpa;

import com.game.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingDAO extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r  ORDER BY r.avgAttempt DESC ")
    List<Rating> findAll();
}
