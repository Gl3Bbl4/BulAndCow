package com.game.controller;

import com.game.service.AttemptService;
import com.game.status.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class AttemptController {
    private AttemptService attemptService;

    @PostMapping("/enterValue")
    public String checkValueByPlayer(@RequestParam("value") String valuePlayer,
                                     @RequestParam("idGame") Long idGame,
                                     Model model) {
        attemptService.checkAnswer(valuePlayer, idGame);
        model.addAttribute("idGame", idGame);
        return "redirect:/";
    }

}
