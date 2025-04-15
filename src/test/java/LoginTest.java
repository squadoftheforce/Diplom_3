import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stellarburger.testui.pages.MainPage;
import ru.stellarburger.testui.pages.StellarBurgerPage;
import ru.stellarburger.testui.user.RandomUser;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stellarburger.testui.Config.*;
import static ru.stellarburger.testui.api.ApiMethods.createUser;

@RunWith(Parameterized.class)
@Feature("Вход пользователя в систему")
public class LoginTest extends BaseTest {

    private final String startPageUrl;
    private final By loginButtonLocator;
    private final String scenarioDescription;

    public LoginTest(String startPageUrl, By loginButtonLocator, String scenarioDescription) {
        this.startPageUrl = startPageUrl;
        this.loginButtonLocator = loginButtonLocator;
        this.scenarioDescription = scenarioDescription;
    }

    /**
     * Формирует набор тестовых сценариев для проверки входа через различные точки
     * @return Коллекция параметров для тестирования
     */
    @Parameterized.Parameters(name = "Вход из {2}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][] {
                {BASE_URI, MainPage.profileButton, "Главная страница через личный кабинет"},
                {BASE_URI, MainPage.goToAccountButton, "Главная страница через кнопку 'Войти в аккаунт'"},
                {RESTORE_PASSWORD_URI, MainPage.loginLink, "Страница восстановления пароля"},
                {REGISTER_URI, MainPage.loginLink, "Страница регистрации"},
        });
    }

    @Test
    @Story("Вход пользователя")
    @Description("Проверка успешной авторизации пользователя через различные точки входа")
    @Step("Вход через {scenarioDescription}")
    public void shouldLoginSuccessfully() {
        RandomUser testUser = new RandomUser();
        createUser(testUser);
        
        StellarBurgerPage page = new StellarBurgerPage(driver);
        page
                .clickLoginButton(startPageUrl, loginButtonLocator)
                .login(testUser);
        
        jwt = ((ChromeDriver) driver).getLocalStorage().getItem("accessToken");
        
        assertThat("JWT токен должен быть получен после успешного входа", jwt, notNullValue());
    }
}
