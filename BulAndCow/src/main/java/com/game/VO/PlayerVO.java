package com.game.VO;

import com.fasterxml.jackson.annotation.JsonView;
import com.game.model.Player;
import com.game.view.Views;
import lombok.Data;

@Data
public class PlayerVO extends ModelVO {
    @JsonView(Views.Public.class)
    private String name;

    public PlayerVO(Player player) {
        super(player.getId());
        this.name = player.getName();
    }
}
