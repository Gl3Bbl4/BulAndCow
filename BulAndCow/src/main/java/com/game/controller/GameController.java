package com.game.controller;

import com.game.model.Player;
import com.game.service.AttemptService;
import com.game.service.GameService;
import com.game.service.PlayerService;
import com.game.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class GameController {

    private GameService gameService;
    private PlayerService playerService;
    private AttemptService attemptService;
    private RatingService ratingService;

    @GetMapping("/new")
    public String startGame(Model model) {
        Long idGame = gameService.startNewGame(playerService.findById((Long) model.getAttribute("idPlayer")));
        model.addAttribute("idGame", idGame);
        return "redirect:/";
    }

    @GetMapping("/")
    public String game(@RequestParam("idGame") Long idGame, Model model) {
        model.addAttribute("idGame", idGame);
        model.addAttribute("stepList", attemptService.getAttemptListByGameId(idGame));
        model.addAttribute("ratingVOList",ratingService.findAllRatingList());
        return "index";
    }
}
