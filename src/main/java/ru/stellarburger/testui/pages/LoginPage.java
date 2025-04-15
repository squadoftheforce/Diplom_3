package ru.stellarburger.testui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stellarburger.testui.user.RandomUser;

import static ru.stellarburger.testui.Config.BASE_URI;
import static ru.stellarburger.testui.Config.TIMEOUT;

public class LoginPage extends StellarBurgerPage{
    public static final By emailField = By.xpath(".//label[text() = 'Email']/../input");
    public static final By passwordField = By.xpath(".//input[@name='Пароль']");

    public static final By loginButton = By.xpath(".//button[text() = 'Войти']");
    public static final By goToRegisterButton = By.xpath(".//a[@href='/register' and text() = 'Зарегистрироваться']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнение полей email и пароль")
    private void fillLoginData(RandomUser user){
        driver.findElement(emailField).sendKeys(user.getEmail());
        driver.findElement(passwordField).sendKeys(user.getPassword());
    }

    @Step("Вход в систему")
    public MainPage login(RandomUser user){
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(emailField));
        fillLoginData(user);

        try {
            new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(loginButton));
            driver.findElement(loginButton).click();
            new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.urlToBe(BASE_URI));
        } catch (TimeoutException e) {

        }
        return new MainPage(driver);
    }

    public LoginPage waitWhilePageLoading(){
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(loginButton));
        return this;
    }

    @Step("Переход на страницу регистрации со страницы входа")
    public RegisterPage clickRegisterLink(){
        WebElement registrationLink = driver.findElement(goToRegisterButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", registrationLink);
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(registrationLink));
        registrationLink.click();
        return new RegisterPage(driver);
    }

}
