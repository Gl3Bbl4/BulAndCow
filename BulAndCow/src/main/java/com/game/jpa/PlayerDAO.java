package com.game.jpa;

import com.game.model.Game;
import com.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDAO extends JpaRepository<Player, Long> {

    List<Player> findAll();

    @Query("SELECT p FROM Player p WHERE p.id = :idPlayer")
    Player findPlayerById(Long idPlayer);

    Player findByMail(String mail);

    @Query("SELECT g.player FROM Game g WHERE g.id = :idGame")
    Player findPlayerByGameId(Long idGame);
}
