package com.game.controller;

import com.game.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RatingController {
    private RatingService ratingService;

    @GetMapping("/rating/all")
    public String ratingList(Model model) {
        model.addAttribute("ratingVOList",ratingService.findAllRatingList());
        return "index";
    }
}
