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
public class Attempt {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private Long id;

    @Column
    private Byte[] value;

    @Column
    private Byte colBul;

    @Column
    private Byte colCow;

    @ManyToOne()
    @JoinColumn
    private Game game;

    public Attempt(Byte colBul, Byte colCow, Byte[] value) {
        this.colBul = colBul;
        this.colCow = colCow;
        this.value = value;
    }
}
