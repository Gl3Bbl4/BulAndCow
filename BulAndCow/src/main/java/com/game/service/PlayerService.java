package com.game.service;

import com.game.model.Player;
import com.game.status.Status;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PlayerService extends UserDetailsService {
    Long save(Player player);

    Player findByMail(String mail);

    Player findById(Long idPlayer);
}
