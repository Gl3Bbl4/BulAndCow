package com.game.service;

import com.game.status.Status;

import java.util.List;

public interface AttemptService {

    Status checkAnswer(String valuePlayer, Long idGame);

    List<String> getAttemptListByGameId(Long idGame);
}
