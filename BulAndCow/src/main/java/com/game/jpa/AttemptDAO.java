package com.game.jpa;

import com.game.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptDAO extends JpaRepository<Attempt, Long> {
    List<Attempt> findAttemptsByGameId(Long id);
}
