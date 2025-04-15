package ru.stellarburger.testui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stellarburger.testui.user.RandomUser;

public class RegisterPage extends StellarBurgerPage{
    public static final By nameField = By.xpath(".//input[@name='name']");
    public static final By emailField = By.xpath(".//label[text() = 'Email']/../input");
    public static final By passwordField = By.xpath(".//input[@name='Пароль']");
    public static final By registerButton = By.xpath(".//button[text() = 'Зарегистрироваться']");
    public static final By shortPasswordError = By.xpath(".//p[@class='input__error text_type_main-default' and text() = 'Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнение данных пользователя на странице регистрации")
    private void fillUserData(RandomUser user){
        driver.findElement(nameField).sendKeys(user.getEmail());
        driver.findElement(emailField).sendKeys(user.getEmail());
        driver.findElement(passwordField).sendKeys(user.getPassword());
    }

    @Step("Регистрация")
    public LoginPage register(RandomUser user){
        fillUserData(user);
        driver.findElement(registerButton).click();
        return new LoginPage(driver);
    }

    @Step("Попытка регистрации")
    public RegisterPage registerFail(RandomUser user){
        fillUserData(user);
        driver.findElement(registerButton).click();
        return this;
    }

    @Step("Проверка отображения ошибки о коротком пароле")
    public boolean passwordErrorShown(){
        return driver.findElement(shortPasswordError).isDisplayed();
    }


}
