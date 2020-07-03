package com.game.controller;

import com.game.model.Player;
import com.game.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PlayerController {
    private PlayerService playerService;

    @GetMapping("/authorization")
    public String registration(Model model) {
        model.addAttribute("player", new Player());
        return "authorization";
    }

    @PostMapping("/reg")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("email") String mail,
                          @RequestParam("password") String password,
                          Model model) {
        Long idPlayer = playerService.save(new Player(name, mail, password));
        if (idPlayer == null) {
            return "authorization";
        }
        model.addAttribute("idPlayer", idPlayer);
        return "redirect:/new";
    }
}
