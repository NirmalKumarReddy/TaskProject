package com.demo.taskproject.payload;

public class JWTAuthResponce {
    private String token;
    private String tokenType="bearer";

    public JWTAuthResponce(String token){
        this.token = token;
    }
}
