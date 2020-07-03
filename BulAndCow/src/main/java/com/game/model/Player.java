package com.game.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Player implements UserDetails {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(unique = true)
    private String mail;

    @Column
    private String name;

    @Column
    private String password;

    @OneToMany(mappedBy = "player")
    private List<Game> gameList;

    @OneToOne
    private Rating rating;

    @ManyToOne()
    @JoinColumn
    private Role role;

    public Player(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return getMail();
    }
}
