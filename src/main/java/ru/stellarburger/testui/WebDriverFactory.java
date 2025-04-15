package ru.stellarburger.testui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    public static final String CHROME_PROPERTY = "chrome";
    public static final String YANDEX_PROPERTY = "yabrowser";
    
    private static final Map<String, WebDriverCreator> BROWSER_CREATORS = new HashMap<>();
    
    static {
        BROWSER_CREATORS.put(CHROME_PROPERTY, WebDriverFactory::createChromeDriver);
        BROWSER_CREATORS.put(YANDEX_PROPERTY, WebDriverFactory::createYandexDriver);
    }
    
    @Step("Инициализация драйвера для браузера: {browser}")
    public static WebDriver getWebDriver() {
        String browser = System.getProperty("WebDriverName");
        WebDriverCreator creator = BROWSER_CREATORS.get(browser);
        
        if (creator == null) {
            throw new WebDriverException("Неподдерживаемый браузер: " + browser);
        }
        
        WebDriver driver = creator.create();
        configureDriver(driver);
        return driver;
    }
    
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    
    private static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }
    
    private static WebDriver createYandexDriver() {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("src/main/resources/yandexdriver.exe"))
                .build();
        return new ChromeDriver(service);
    }
    
    @FunctionalInterface
    private interface WebDriverCreator {
        WebDriver create();
    }
}
