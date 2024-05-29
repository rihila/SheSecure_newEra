package com.example.shesecure10.models;

public class User {

    String username, email , passwords, userid , sosemail1, sosemail2 , sosemail3;

    public User(String username, String email, String passwords, String userid, String sosemail1, String sosemail2, String sosemail3) {
        this.username = username;
        this.email = email;
        this.passwords = passwords;
        this.userid = userid;
        this.sosemail1 = sosemail1;
        this.sosemail2 = sosemail2;
        this.sosemail3 = sosemail3;
    }

    public User(){}

    public User(String username, String email, String passwords) {
        this.username = username;
        this.email = email;
        this.passwords = passwords;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSosemail1() {
        return sosemail1;
    }

    public void setSosemail1(String sosemail1) {
        this.sosemail1 = sosemail1;
    }

    public String getSosemail2() {
        return sosemail2;
    }

    public void setSosemail2(String sosemail2) {
        this.sosemail2 = sosemail2;
    }

    public String getSosemail3() {
        return sosemail3;
    }

    public void setSosemail3(String sosemail3) {
        this.sosemail3 = sosemail3;
    }
}
