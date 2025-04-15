package ru.stellarburger.testui;

import java.util.Random;

public class RandomData {

    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static String randomPassword() {
        int length = 6 + random.nextInt(5); // от 6 до 10 символов
        return generateRandomString(length);
    }

    public static String randomEmail() {
        int usernameLength = 5 + random.nextInt(6); // от 5 до 10 символов
        int domainLength = 5 + random.nextInt(6); // от 5 до 10 символов
        
        String username = generateRandomString(usernameLength);
        String domain = generateRandomString(domainLength);
        
        return username + "@" + domain + ".com";
    }

    public static String randomName() {
        int length = 5 + random.nextInt(6); // от 5 до 10 символов
        return generateRandomString(length);
    }
    
    private static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC.length());
            builder.append(ALPHA_NUMERIC.charAt(index));
        }
        return builder.toString();
    }
}
