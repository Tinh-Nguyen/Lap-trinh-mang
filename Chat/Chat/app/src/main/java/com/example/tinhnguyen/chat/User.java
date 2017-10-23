package com.example.tinhnguyen.chat;

/**
 * Created by Tinh Nguyen on 9/10/2017.
 */

public class User {
    public String userName,email,pass,avatar;
    public User(){}
    public User(String userName ,String email, String pass, String avatar){
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.avatar = avatar;
    }
}
