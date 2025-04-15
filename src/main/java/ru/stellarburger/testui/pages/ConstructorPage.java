package ru.stellarburger.testui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class ConstructorPage extends StellarBurgerPage{

    private static final By enabledTabXpath = By.xpath(".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']");
    private static final String tabTextXpath = ".//span[text() = '$tabName$']/parent::div";

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Выбор вкладки с указанным именем")
    public ConstructorPage chooseTab(String tabName){
        if (!tabIsSelected(tabName)){
            By tabButton = By.xpath(tabTextXpath.replace("$tabName$", tabName));
            WebElement tabElement = driver.findElement(tabButton);
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", tabElement);
        }
        return this;
    }

    @Step("Проверка, что нужная секция выбрана")
    public boolean tabIsSelected(String tabName){
        WebElement selectedTab = driver.findElement(enabledTabXpath);
        return selectedTab.getText().equals(tabName);
    }

}
