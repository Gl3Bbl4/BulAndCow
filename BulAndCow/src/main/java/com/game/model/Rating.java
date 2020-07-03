package com.game.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @Column
    private Float avgAttempt;

    @Column
    private boolean isСalculated;

    @OneToOne
    private Player player;

    public Rating(Float avgAttempt, Player player, boolean isСalculated) {
        this.avgAttempt = avgAttempt;
        this.player = player;
        this.isСalculated = isСalculated;
    }
}
