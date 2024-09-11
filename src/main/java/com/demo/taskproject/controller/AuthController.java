package com.demo.taskproject.controller;

import com.demo.taskproject.entity.Users;
import com.demo.taskproject.payload.JWTAuthResponce;
import com.demo.taskproject.payload.LoginDto;
import com.demo.taskproject.payload.UserDto;
import com.demo.taskproject.security.JwtTokenProvider;
import com.demo.taskproject.service.UserService;
import org.antlr.v4.runtime.misc.LogManager;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    //post store the user in DB

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
    return  new ResponseEntity<>(userService.createUser(userDto),HttpStatus.CREATED);
    }

@PostMapping("/login")
    public ResponseEntity<JWTAuthResponce> loginUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponce(token));
    }

}
