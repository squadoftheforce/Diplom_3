import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.stellarburger.testui.pages.ConstructorPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static ru.stellarburger.testui.Config.BASE_URI;

@RunWith(Parameterized.class)
@Feature("Конструктор бургеров")
public class ChangeCategoryTest extends BaseTest {
    
    private final String sourceCategory;
    private final String targetCategory;

    public ChangeCategoryTest(String sourceCategory, String targetCategory) {
        this.sourceCategory = sourceCategory;
        this.targetCategory = targetCategory;
    }

    /**
     * Создает набор тестовых данных для параметризованного теста
     * Каждая комбинация проверяет переключение между разными категориями
     */
    @Parameterized.Parameters(name = "Переключение из {0} в {1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"Булки", "Начинки"},
                {"Булки", "Соусы"},
                {"Начинки", "Булки"},
                {"Соусы", "Булки"},
        });
    }

    @Test
    @Story("Переключение между категориями ингредиентов")
    @Description("Проверяет корректность переключения между разными категориями ингредиентов в конструкторе")
    @Step("Проверка переключения категорий: из {sourceCategory} в {targetCategory}")
    public void shouldSwitchCategorySuccessfully() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
        driver.get(BASE_URI);
        
        ConstructorPage constructorPage = new ConstructorPage(driver);
        
        constructorPage
                .chooseTab(sourceCategory)
                .chooseTab(targetCategory);
        
        boolean isCategorySelected = constructorPage.tabIsSelected(targetCategory);
        
        assertTrue(String.format("Категория '%s' должна быть выбрана после переключения", targetCategory), 
                   isCategorySelected);
    }
}
