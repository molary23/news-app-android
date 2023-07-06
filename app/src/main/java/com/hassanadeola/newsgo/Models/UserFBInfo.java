package com.hassanadeola.newsgo.Models;

public class UserFBInfo {
    String username = null, email = null, os = "android", token = null;
    boolean pushNotify = false, emailNotify = false;


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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String device) {
        this.token = device;
    }

    public boolean isPushNotify() {
        return pushNotify;
    }

    public void setPushNotify(boolean pushNotify) {
        this.pushNotify = pushNotify;
    }

    public boolean isEmailNotify() {
        return emailNotify;
    }

    public void setEmailNotify(boolean emailNotify) {
        this.emailNotify = emailNotify;
    }
}
