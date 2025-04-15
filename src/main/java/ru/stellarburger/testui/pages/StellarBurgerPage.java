package ru.stellarburger.testui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StellarBurgerPage {
    private static final By LogoLink = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");
    private static final By constructorLink = By.xpath(".//p[@class = 'AppHeader_header__linkText__3q_va ml-2' and text() = 'Конструктор']");

    WebDriver driver;

    public StellarBurgerPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по конструктору в шапке")
    public MainPage clickConstructor(){
        driver.findElement(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("Клик по логотипу в шапке")
    public MainPage clickLogoLink(){
        driver.findElement(LogoLink).click();
        return new MainPage(driver);
    }

    @Step("Клик по кнопке входа на странице")
    public LoginPage clickLoginButton(String startPage, By locator){
        driver.get(startPage);
        WebElement profile = driver.findElement(locator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", profile);
        return new LoginPage(driver);
    }

}
