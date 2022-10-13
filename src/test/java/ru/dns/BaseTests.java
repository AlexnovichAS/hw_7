package ru.dns;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.dns.managers.DriverManager;
import ru.dns.managers.InitManager;
import ru.dns.managers.PageManager;
import ru.dns.managers.TestPropManager;
import ru.dns.pages.BasePage;

import static ru.dns.utils.PropConst.BASE_URL;

/**
 * Базовый класс для тестов
 * @author Алехнович Александр
 */
public class BaseTests {
    /**
     * Менеджер страничек
     *
     * @author Алехнович Александр
     * @see PageManager#getPageManager()
     */
    protected PageManager app = PageManager.getPageManager();

    /**
     * Менеджер WebDriver
     *
     * @author Алехнович Александр
     * @see DriverManager#getDriverManager()
     */
    private final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Перед всеми тестами открывает браузер на весь экран и устанавливает ожидания
     *
     * @author Алехнович Александр
     */
    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    /**
     * Перед каждым тестом открывает страницу сайта
     *
     * @author Алехнович Александр
     */
    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(BASE_URL));
    }


    /**
     * После каждого теста очищает Cookies и присваивает null объекту {@link BasePage}
     *
     * @author Алехнович Александр
     */
    @AfterEach
    public void afterEach() {
        app.getCloseBasePage();
        driverManager.getDriver().manage().deleteAllCookies();
    }

    /**
     * После всех тестов закрывает браузер
     *
     * @author Алехнович Александр
     */
    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
