package com.game.service;

import com.game.jpa.GameDAO;
import com.game.jpa.PlayerDAO;
import com.game.model.Game;
import com.game.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    public static final byte LENGTH_CODE = 4;

    private GameDAO gameDAO;

    @Override
    @Transactional
    public Long startNewGame(Player player) {
        Game game = new Game();
        game.setPlayer(player);
        game.setEnd(false);
        game.setRight_Value(generateRightValue());
        return gameDAO.save(game).getId();
    }

    private Byte[] generateRightValue() {
        Byte[] rightValue = new Byte[LENGTH_CODE];
        Set<Byte> uniqueValue = new HashSet<>();
        Random rn = new Random();

        for (int i = 0; i < rightValue.length; i++) {
            byte value = (byte) rn.nextInt(9);
            while(!uniqueValue.add(value)){
                value = (byte) rn.nextInt(9);
            }
            rightValue[i] = value;
        }
        return rightValue;
    }
}
