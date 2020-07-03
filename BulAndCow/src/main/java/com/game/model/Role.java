package com.game.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Role{

    @Id
    @GeneratedValue
    @Column
    Long id;
    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<Player> playerList;

    public Role(String name) {
        this.name = name;
    }

}
