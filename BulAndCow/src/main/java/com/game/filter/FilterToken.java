package com.game.filter;

import com.game.config.JwtConfig;
import com.game.model.Player;
import com.game.service.PlayerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FilterToken extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private PlayerService playerService;

    public FilterToken(JwtConfig jwtConfig, PlayerService playerService) {
        this.jwtConfig = jwtConfig;
        this.playerService = playerService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(jwtConfig.getHeader());
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = header.replace(jwtConfig.getPrefix(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            String mail = claims.getSubject();
            List<LinkedHashMap> authoritiesMap = (List<LinkedHashMap>) claims.get("authorities");
            if (mail != null) {
                Player player = playerService.findByMail(mail);
                if (authoritiesMap != null) {
                    List<SimpleGrantedAuthority> authorities = authoritiesMap
                            .stream().map(val -> {
                                        LinkedHashMap val1 = val;
                                        return new SimpleGrantedAuthority((String) val1.get("authority"));
                                    }
                            ).collect(Collectors.toList());
                    ;
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            player, null, authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
