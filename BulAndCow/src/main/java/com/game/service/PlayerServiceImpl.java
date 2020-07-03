package com.game.service;

import com.game.jpa.PlayerDAO;
import com.game.jpa.RatingDAO;
import com.game.jpa.RoleDAO;
import com.game.model.Player;
import com.game.model.Rating;
import com.game.model.Role;
import com.game.status.Status;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private PlayerDAO playerDAO;
    private RoleDAO roleDAO;
    private RatingDAO ratingDAO;

    @Override
    @Transactional
    public Long save(Player player) {
        Player playerFromDB = playerDAO.findByMail(player.getMail());
        if (playerFromDB != null) {
            return null;
        }
        if (roleDAO.findByName("ROLE_USER") == null) {
            roleDAO.save(new Role("ROLE_USER"));
        }
        Role roleUser = roleDAO.findByName("ROLE_USER");
        Rating rating = new Rating((float)0, player, false);
        ratingDAO.save(rating);

        player.setRole(roleUser);
        player.setRating(rating);
        player.setPassword(new BCryptPasswordEncoder().encode(player.getPassword()));
        return playerDAO.save(player).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Player player = playerDAO.findByMail(s);
        return player;
    }

    @Override
    public Player findByMail(String mail) {
        return playerDAO.findByMail(mail);
    }

    @Override
    public Player findById(Long idPlayer) {
        return playerDAO.findPlayerById(idPlayer);
    }
}
