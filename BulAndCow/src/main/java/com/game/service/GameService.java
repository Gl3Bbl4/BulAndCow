package com.game.service;

import com.game.model.Player;
import org.springframework.security.core.Authentication;

public interface GameService {

    Long startNewGame(Player player);

}
