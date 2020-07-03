package com.game.service;

import com.game.jpa.AttemptDAO;
import com.game.jpa.GameDAO;
import com.game.model.Attempt;
import com.game.model.Game;
import com.game.status.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.game.service.GameServiceImpl.LENGTH_CODE;

@Service
@AllArgsConstructor
public class AttemptServiceImpl implements AttemptService {
    private AttemptDAO attemptDAO;
    private GameDAO gameDAO;

    @Transactional
    public Status save(Attempt attempt, Game game) {
        boolean isGuess = attempt.getColBul() == LENGTH_CODE ? true : false;
        if (isGuess) {
            game.getPlayer().getRating().setСalculated(false);
            game.setEnd(true);
        }
        attempt.setGame(game);
        attemptDAO.save(attempt);
        return isGuess ? Status.SUCCESS : Status.FAIL;
    }

    @Override
    public List<String> getAttemptListByGameId(Long idGame) {
        return fromAttemptToStringFormat(attemptDAO.findAttemptsByGameId(idGame));
    }

    //Формирование строки для игрока с угаданными значениями
    private List<String> fromAttemptToStringFormat(List<Attempt> attemptList) {
        List<String> bulAndColList = new ArrayList<>();
        for (Attempt attempt : attemptList) {
            String bulAndCol = attempt.getColBul() + "Б" + attempt.getColCow() + "К" + " - "
                    + fromByteTostring(attempt.getValue());
            bulAndColList.add(bulAndCol);
        }
        return bulAndColList;
    }

    private String fromByteTostring(Byte[] byteList) {
        String str = new String();
        for (Byte _byte : byteList) {
            str += _byte;
        }
        return str;
    }

    @Override
    public Status checkAnswer(String _valuePlayer, Long idGame) {
        Game game = gameDAO.findGameById(idGame);
        if (game == null) return Status.WRONG;
        if (game.isEnd()) return Status.IS_END;
        if (checkColAndRepeatingValue(_valuePlayer) == Status.WRONG) return Status.WRONG;

        Byte[] valuePlayer = parsingValuePlayerForByteArr(_valuePlayer);
        return calculatedBulAndCow(valuePlayer, game);
    }

    //Проверка на валидность ответа присланного игроком
    private Status checkColAndRepeatingValue(String _valuePlayer) {
        char[] _valuePlayerChar = _valuePlayer.toCharArray();
        if (_valuePlayerChar.length != LENGTH_CODE
                || checkValuePlayerNotIsDigit(_valuePlayerChar)
                || checkRepeating(_valuePlayerChar)) {
            return Status.WRONG;
        }
        return Status.SUCCESS;
    }

    //Проверка, что числа не повторяются
    private boolean checkRepeating(char[] _valuePlayerChar) {
        Set<Character> listChar = new HashSet<>();
        for (Character character : _valuePlayerChar) {
            listChar.add(character);
        }
        if (listChar.size() == LENGTH_CODE) return false;
        return true;
    }

    //Проверка, что все значения присланные игроком - числа
    private boolean checkValuePlayerNotIsDigit(char[] _valuePlayerChar) {
        for (int i = 0; i < _valuePlayerChar.length; i++) {
            if (!Character.isDigit(_valuePlayerChar[i])) {
                return true;
            }
        }
        return false;
    }

    //Подсчет угаданных числе
    private Status calculatedBulAndCow(Byte[] valuePlayer, Game game) {
        byte colBul = 0, colCow = 0;
        Byte[] rightValue = gameDAO.findRightValueById(game.getId());
        for (int i = 0; i < LENGTH_CODE; i++) {
            if (rightValue[i].equals(valuePlayer[i])) {
                colBul++;
            } else {
                for (int j = 0; j < LENGTH_CODE; j++) {
                    if (rightValue[i].equals(valuePlayer[j])) {
                        colCow++;
                        break;
                    }
                }
            }
        }
        return save(new Attempt(colBul, colCow, valuePlayer), game);
    }

    //Парсинг строки от игрока в массив байт
    private Byte[] parsingValuePlayerForByteArr(String _valuePlayer) {
        Byte[] valuePlayer = new Byte[LENGTH_CODE];
        for (int i = 0; i < _valuePlayer.toCharArray().length; i++) {
            valuePlayer[i] = (byte) Character.getNumericValue(_valuePlayer.toCharArray()[i]);
        }
        return valuePlayer;
    }
}
