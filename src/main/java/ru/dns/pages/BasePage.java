package ru.dns.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dns.data.Product;
import ru.dns.managers.DriverManager;
import ru.dns.managers.PageManager;
import ru.dns.managers.TestPropManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.dns.utils.PropConst.IMPLICITLY_WAIT;

/**
 * Базовый класс всех страничек
 */
public class BasePage {

    private final List<Product> listProductsAndPrice = new ArrayList<>();

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     *
     * @see PageManager
     */
    protected PageManager pageManager = PageManager.getPageManager();


    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     *
     * @see Actions
     */
    protected Actions action = new Actions(driverManager.getDriver());


    /**
     * Объект для выполнения любого js кода
     *
     * @see JavascriptExecutor
     */
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();


    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     *
     * @see WebDriverWait
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);


    /**
     * Менеджер properties
     *
     * @see TestPropManager#getTestPropManager()
     */
    private final TestPropManager props = TestPropManager.getTestPropManager();


    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     * Подробнее можно просмотреть в класс {@link PageFactory}
     *
     * @see FindBy
     * @see PageFactory
     * @see PageFactory#initElements(WebDriver, Object)
     */
    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }


    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     * @see JavascriptExecutor
     */
    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    /**
     * Клик по элементу на js коде
     *
     * @param element - веб элемент на который нужно кликнуть
     * @author Алехнович Александр
     */
    public void elementClickJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driverManager.getDriver();
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js со смещение
     * Смещение задается количеством пикселей по вертикали и горизонтали, т.е. смещение до точки (x, y)
     *
     * @param element - веб-элемент странички
     * @param x       - параметр координаты по горизонтали
     * @param y       - параметр координаты по вертикали
     * @see JavascriptExecutor
     */
    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }


    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     * @see WebDriverWait
     * @see org.openqa.selenium.support.ui.FluentWait
     * @see org.openqa.selenium.support.ui.Wait
     * @see ExpectedConditions
     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видемым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Скрол до элемента через Actions
     *
     * @param element - веб элемент до которого нужно проскролить
     * @author Алехнович Александр
     */
    public WebElement scrollToElementActions(WebElement element) {
        Actions actions = new Actions(driverManager.getDriver());
        actions.moveToElement(element).build().perform();
        return element;
    }

    protected List<Product> saveListProducts(Product product){
        listProductsAndPrice.add(product);
        return listProductsAndPrice;
    }

    protected List<Product> deleteProduct(Product product){
        if (listProductsAndPrice.stream().anyMatch(s -> s.getName().equalsIgnoreCase(product.getName()))) {
            listProductsAndPrice.removeIf(s -> s.getName().equalsIgnoreCase(product.getName()));
        }
        return listProductsAndPrice;
    }

    protected List<Product> getListProducts(){
        return listProductsAndPrice;
    }
    /**
     * Проверяет наличие элемента на странице
     *
     * @param element - веб элемент который нужно найти
     * @author Алехнович Александр
     */
    public boolean isElementExist(By element) {
        try {
            driverManager.getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driverManager.getDriver().findElement(element);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
    }

}
