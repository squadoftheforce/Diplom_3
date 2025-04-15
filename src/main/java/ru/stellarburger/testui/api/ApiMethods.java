package ru.stellarburger.testui.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.stellarburger.testui.user.RandomUser;

import static io.restassured.RestAssured.given;
import static ru.stellarburger.testui.Config.BASE_URI;

public class ApiMethods {

    public final static String API = "api/";
    public final static String DELETE_USER_API = "auth/user";
    public final static String CREATE_USER_API = "auth/register";
    public final static String LOGIN_API = "auth/login";

    @Step("Удаление пользователя через API")
    public static void deleteUser(String jwt){
        jwt = jwt.split(" ")[1];
        spec()
                .auth().oauth2(jwt)
                .delete(DELETE_USER_API)
                .then()
                .log().all();
    }

    public static void deleteUser(RandomUser user){
        deleteUser(user.getAccessToken());
    }

    @Step("Создание пользователя через API")
    public static void createUser(RandomUser user){
            String jwt = spec()
                    .contentType(ContentType.JSON)
                    .body(user)
                    .post(CREATE_USER_API)
                    .body().as(RandomUser.class).getAccessToken();
            user.setAccessToken(jwt);
    }

    @Step("Вход в систему через API")
    public static void login(RandomUser user){
        String jwt = spec()
                .contentType(ContentType.JSON)
                .body(user)
                .post(LOGIN_API)
                .then()
                .extract()
                .path("accessToken");
        user.setAccessToken(jwt);
    }

    private static RequestSpecification spec(){
        return given().log().all()
                .baseUri(BASE_URI)
                .basePath(API);
    }

}

