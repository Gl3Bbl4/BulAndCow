package com.game.VO;

import com.fasterxml.jackson.annotation.JsonView;
import com.game.view.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelVO {
    @JsonView(Views.Public.class)
    private Long id;
}
