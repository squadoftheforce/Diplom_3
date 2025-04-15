import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stellarburger.testui.api.ApiMethods;
import ru.stellarburger.testui.pages.LoginPage;
import ru.stellarburger.testui.pages.MainPage;
import ru.stellarburger.testui.user.RandomUser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stellarburger.testui.Config.*;

@Feature("Личный кабинет")
public class ProfileTest extends BaseTest {

    private RandomUser testUser;
    private MainPage homePage;

    @Before
    @Step("Подготовка тестовых данных и авторизация пользователя")
    public void prepareTestEnvironment() {
        testUser = new RandomUser();
        ApiMethods.createUser(testUser);
        
        driver.get(LOGIN_URI);
        homePage = new LoginPage(driver).login(testUser);
        
        jwt = ((ChromeDriver) driver).getLocalStorage().getItem("accessToken");
    }

    @Test
    @Story("Навигация в личный кабинет")
    @Description("Проверка перехода в личный кабинет по клику на кнопку 'Личный кабинет'")
    @Step("Проверка навигации в личный кабинет")
    public void shouldNavigateToProfilePageSuccessfully() {
        homePage
                .profileButtonClickAfterLogin()
                .waitWhilePageLoading();
        
        assertThat("URL должен соответствовать странице профиля", 
                   driver.getCurrentUrl(), equalTo(PROFILE_URI));
    }

    @Test
    @Story("Навигация из личного кабинета")
    @Description("Проверка перехода из личного кабинета в конструктор")
    @Step("Проверка перехода из личного кабинета в конструктор")
    public void shouldNavigateFromProfileToConstructorSuccessfully() {
        homePage
                .profileButtonClickAfterLogin()
                .clickConstructor()
                .waitWhilePageLoading(BASE_URI);
        
        assertThat("URL должен соответствовать главной странице", 
                   driver.getCurrentUrl(), equalTo(BASE_URI));
    }

    @Test
    @Story("Навигация из личного кабинета")
    @Description("Проверка перехода из личного кабинета на главную страницу по логотипу")
    @Step("Проверка перехода из личного кабинета на главную по логотипу")
    public void shouldNavigateFromProfileToHomePageSuccessfully() {
        homePage
                .profileButtonClickAfterLogin()
                .clickLogoLink()
                .waitWhilePageLoading(BASE_URI);
        
        assertThat("URL должен соответствовать главной странице", 
                   driver.getCurrentUrl(), equalTo(BASE_URI));
    }

    @Test
    @Story("Выход из аккаунта")
    @Description("Проверка выхода из аккаунта по кнопке 'Выйти' в личном кабинете")
    @Step("Проверка процесса выхода из аккаунта")
    public void shouldLogoutSuccessfully() {
        homePage
                .profileButtonClickAfterLogin()
                .waitWhilePageLoading()
                .clickExitButton()
                .waitWhilePageLoading();
        
        assertThat("После выхода должен быть редирект на страницу логина", 
                   driver.getCurrentUrl(), equalTo(LOGIN_URI));
        
        String accessToken = ((ChromeDriver)driver).getLocalStorage().getItem("accessToken");
        assertThat("Токен доступа должен быть удален после выхода", 
                   accessToken, nullValue());
    }
}
