package ru.stellarburger.testui.user;

import static ru.stellarburger.testui.RandomData.*;
import static ru.stellarburger.testui.Config.MIN_PASSWORD_LENGTH;

public class RandomUser{
    private String name;
    private String email;
    private String password;
    private String accessToken;

    public RandomUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public RandomUser(String name, String email, String password, String accessToken) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
    }

    public RandomUser() {
        this.name = randomName();
        this.email = randomEmail();
        this.password = randomPassword();
    }

    public RandomUser cutPassword(){
        password = password.substring(0, MIN_PASSWORD_LENGTH - 1);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
