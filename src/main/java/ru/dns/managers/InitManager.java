package ru.dns.managers;

import java.util.concurrent.TimeUnit;

import static ru.dns.utils.PropConst.IMPLICITLY_WAIT;
import static ru.dns.utils.PropConst.PAGE_LOAD_TIMEOUT;

/**
 * Класс для инициализации фреймворка
 * @author Алехнович Александр
 */
public class InitManager {

    /**
     * Менеджер properties
     * @author Алехнович Александр
     * @see TestPropManager#getTestPropManager()
     */
    private static final TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Менеджер WebDriver
     * @author Алехнович Александр
     * @see DriverManager#getDriverManager()
     */
    private static final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Инициализация framework и запуск браузера со страницей приложения
     * @author Алехнович Александр
     * @see DriverManager
     * @see TestPropManager#getProperty(String)
     * @see ru.dns.utils.PropConst
     */
    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    /**
     * Завершения работы framework - гасит драйвер и закрывает сессию с браузером
     * @author Алехнович Александр
     * @see DriverManager#quitDriver()
     */
    public static void quitFramework() {
        driverManager.quitDriver();
    }
}
