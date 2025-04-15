import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static ru.stellarburger.testui.WebDriverFactory.getWebDriver;
import static ru.stellarburger.testui.api.ApiMethods.deleteUser;

/**
 * Базовый класс для всех тестовых классов проекта.
 * Обеспечивает основную инфраструктуру тестирования:
 * - Инициализацию и завершение работы WebDriver
 * - Очистку тестовых данных
 * - Логирование и создание скриншотов при падении тестов
 */
public class BaseTest {
    
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected String jwt;
    
    @Rule
    public TestName testName = new TestName();
    
    @Rule
    public TestWatcher screenshotRule = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            log.info("Тест не пройден: {}", description.getMethodName());
            captureScreenshot(description.getMethodName());
        }
        
        @Override
        protected void succeeded(Description description) {
            log.info("Тест успешно пройден: {}", description.getMethodName());
        }
    };

    /**
     * Инициализация окружения перед каждым тестом
     */
    @Before
    @Step("Подготовка тестового окружения")
    public void setUp() {
        log.info("Запуск теста: {}", testName.getMethodName());
        
        // Инициализация WebDriver с указанным типом браузера
        driver = getWebDriver();
        
        // Настройка размера окна браузера
        driver.manage().window().setSize(new Dimension(1280, 800));
        
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Освобождение ресурсов после выполнения теста
     */
    @After
    @Step("Очистка ресурсов после выполнения теста")
    public void cleanUp() {
        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
        
        // Если был создан пользователь, удаляем его через API
        if (jwt != null && !jwt.isEmpty()) {
            try {
                deleteUser(jwt);
                log.info("Тестовый пользователь успешно удален");
            } catch (Exception e) {
                log.error("Ошибка при удалении тестового пользователя: {}", e.getMessage());
            }
        }
    }
    
    /**
     * Создает скриншот текущего состояния браузера
     * @param methodName имя метода, для которого создается скриншот
     */
    private void captureScreenshot(String methodName) {
        if (driver instanceof TakesScreenshot) {
            try {
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                log.info("Скриншот сохранен для метода: {}", methodName);
            } catch (Exception e) {
                log.error("Не удалось создать скриншот: {}", e.getMessage());
            }
        }
    }
}
