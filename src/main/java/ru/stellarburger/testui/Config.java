package ru.stellarburger.testui;

public class Config {
    public final static String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    public final static String LOGIN_URI = BASE_URI + "login";
    public final static String REGISTER_URI = BASE_URI + "register";
    public final static String RESTORE_PASSWORD_URI = BASE_URI + "forgot-password";
    public final static String ACCOUNT_URI = BASE_URI + "account/";
    public final static String PROFILE_URI = ACCOUNT_URI + "profile";

    public final static int MIN_PASSWORD_LENGTH = 6;
    public static final int TIMEOUT = 15;
}
