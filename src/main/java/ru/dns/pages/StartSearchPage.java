package ru.dns.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Алехнович Александр
 * Класс описывающий страницу поиска
 */
public class StartSearchPage extends BasePage{

    /**
     * Поиск поля ввода
     * @author Алехнович Александр
     */
    @FindBy(xpath = "//div[@class='header-menu-wrapper']//input[@type='search']")
    private WebElement input;

    /**
     * Поиск кнопки "Найти"
     * @author Алехнович Александр
     */
    @FindBy(xpath = "//div[@class='header-menu-wrapper']//span[contains(@class,'ui-input-search__icon_search')]")
    private WebElement search;

    /**
     * Поиск кнопки "Найти"
     * @author Алехнович Александр
     * @param product - значение вводимое в поле поиска
     * @return SearсhResultsPage - переходим на страницу результатов поиска {@link SearсhResultsPage}
     */
    public SearсhResultsPage find(String product) {
        input.sendKeys(product);
        search.click();
        return pageManager.getSearсhResultsPage();
    }
}
