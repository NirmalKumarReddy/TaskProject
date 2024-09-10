package com.demo.taskproject.repository;

import ch.qos.logback.core.util.StringUtil;
import com.demo.taskproject.security.CustomUserDetailsService;
import com.demo.taskproject.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        //get the token
        String token = getToken(request);
        //check the token either vaildor not

        if(StringUtils.hasText(token)&& jwtTokenProvider.validateToken(token)){
            String email = jwtTokenProvider.getEmailFromToken(token);
            UserDetails userDetails= customUserDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication  =
                    new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);


    }
        private String getToken(HttpServletRequest request){
            String token = request.getHeader("Authorization");
            if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
                return token.substring(7, token.length());
            }
            return null;

        }

    }

