package ru.stellarburger.testui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static ru.stellarburger.testui.Config.TIMEOUT;

public class ProfilePage extends StellarBurgerPage{
    public static final By exitProfileButton = By.xpath(".//button[text() = 'Выход']");
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage waitWhilePageLoading(){
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(exitProfileButton));
        return this;
    }

    public LoginPage clickExitButton(){
        WebElement exitButton = driver.findElement(exitProfileButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", exitButton);
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(exitButton));
        exitButton.click();
        return new LoginPage(driver);
    }
}
