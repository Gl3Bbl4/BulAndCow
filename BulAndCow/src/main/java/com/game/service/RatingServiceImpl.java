package com.game.service;

import com.game.VO.RatingVO;
import com.game.jpa.PlayerDAO;
import com.game.jpa.RatingDAO;
import com.game.model.Attempt;
import com.game.model.Game;
import com.game.model.Player;
import com.game.model.Rating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private RatingDAO ratingDAO;
    private PlayerDAO playerDAO;

    //Подсчет рейтинга всех пользователей
    public void updateRatingList() {
        List<Player> playerList = playerDAO.findAll();
        for (Player player : playerList) {
            float colAllAttemts = 0, colGameIsEnd = 0;

            List<Game> gameList = player.getGameList();
            Rating rating = player.getRating();
            if (!rating.isСalculated()) {
                for (Game game : gameList) {
                    if (game.isEnd()) {
                        colAllAttemts += game.getAttemptList().size();
                        colGameIsEnd++;
                    }
                }

                if(colAllAttemts!=0) rating.setAvgAttempt( colGameIsEnd / colAllAttemts);
                rating.setСalculated(true);
            }
        }
    }

    @Override
    public List<RatingVO> findAllRatingList() {
        updateRatingList();
        return ratingDAO.findAll().stream()
                .map(RatingVO::new)
                .collect(Collectors.toList());
    }
}
