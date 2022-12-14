package com.gambit.GamBit.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gambit.GamBit.exception.UserNotAuthorizedException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.security.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.gambit.GamBit.GamBitApplication.staticEntities;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login")){
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer_")){
                try{
                    String token = authorizationHeader.substring("Bearer_".length());
                    System.out.println(token);

                    Algorithm algorithm = Algorithm.HMAC256("secretPassword123");
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String username = decodedJWT.getSubject();
                    System.out.println(username);
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    if(authorities.contains(new SimpleGrantedAuthority("2FA"))){
                        if(!staticEntities.passedVerificationUsers.contains(username)){

                            throw  new UserNotAuthorizedException("2FA not passed");
                        }
                    }
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception){
                    System.out.println("Error occurred during authorization");
                    response.sendError(FORBIDDEN.value());
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }

    }
}
