package com.game.jpa;

import com.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameDAO extends JpaRepository<Game, Long> {
    @Query(value = "SELECT g.right_Value FROM Game g WHERE g.id = :id", nativeQuery = true)
    Byte[] findRightValueById(@Param("id") Long id);

    @Query("SELECT g FROM Game g WHERE g.id = :idGame")
    Game findGameById(Long idGame);

    @Query("SELECT g FROM Game g WHERE g.player = :idPlayer")
    List<Game> findGameByPlayerId(Long idPlayer);
}
