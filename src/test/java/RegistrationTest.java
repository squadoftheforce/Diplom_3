import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stellarburger.testui.api.ApiMethods;
import ru.stellarburger.testui.pages.MainPage;
import ru.stellarburger.testui.user.RandomUser;

import static org.junit.Assert.*;
import static ru.stellarburger.testui.Config.*;

@Feature("Регистрация пользователей")
public class RegistrationTest extends BaseTest {

    private RandomUser testUser;
    private MainPage homePage;

    @Before
    @Step("Инициализация тестовых данных для проверки регистрации")
    public void initTestData() {
        homePage = new MainPage(driver);
        testUser = new RandomUser();
        
        driver.get(BASE_URI);
    }

    @Test
    @Story("Успешная регистрация")
    @Description("Проверка перенаправления на страницу входа после успешной регистрации")
    @Step("Проверка редиректа на страницу входа после регистрации")
    public void shouldRedirectToLoginPageAfterRegistration() {
        homePage.profileButtonClickBeforeLogin()
                .clickRegisterLink()
                .register(testUser)
                .waitWhilePageLoading();
                
        boolean redirectedToLogin = driver.getCurrentUrl().equals(LOGIN_URI);
        
        assertTrue("После регистрации должен быть редирект на страницу логина", redirectedToLogin);
    }

    @Test
    @Story("Успешная регистрация")
    @Description("Проверка возможности входа в систему после успешной регистрации")
    @Step("Проверка возможности входа после регистрации пользователя")
    public void shouldCreateUserSuccessfully() {
        homePage.profileButtonClickBeforeLogin()
                .clickRegisterLink()
                .register(testUser)
                .waitWhilePageLoading()
                .login(testUser);
                
        jwt = ((ChromeDriver) driver).getLocalStorage().getItem("accessToken");
        
        assertNotNull("После успешной регистрации и входа должен быть получен JWT токен", jwt);
    }

    @Test
    @Story("Валидация пароля")
    @Description("Проверка отображения ошибки при вводе некорректного (короткого) пароля")
    @Step("Проверка отображения ошибки при вводе короткого пароля")
    public void shouldShowErrorForShortPassword() {
        boolean errorMessageDisplayed = homePage.profileButtonClickBeforeLogin()
                .clickRegisterLink()
                .registerFail(testUser.cutPassword())
                .passwordErrorShown();
                
        assertTrue("Должно отображаться сообщение об ошибке для короткого пароля", errorMessageDisplayed);
    }

    @Test
    @Story("Валидация пароля")
    @Description("Проверка невозможности создания пользователя с некорректным (коротким) паролем")
    @Step("Проверка невозможности регистрации с коротким паролем")
    public void shouldNotCreateUserWithShortPassword() {
        homePage.profileButtonClickBeforeLogin()
                .clickRegisterLink()
                .register(testUser.cutPassword());

        ApiMethods.login(testUser);
        jwt = testUser.getAccessToken();
        
        assertNull("Пользователь не должен быть создан с коротким паролем", jwt);
    }

    @After
    @Step("Попытка получения JWT для очистки тестовых данных")
    public void tryGetJwtForCleanup() {
        if (jwt == null || jwt.isEmpty()) {
            ApiMethods.login(testUser);
            jwt = testUser.getAccessToken();
        }
    }
}
