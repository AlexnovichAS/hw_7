package ru.dns.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartSearchPage extends BasePage{

    @FindBy(xpath = "//div[@class='header-menu-wrapper']//input[@type='search']")
    private WebElement input;

    @FindBy(xpath = "//div[@class='header-menu-wrapper']//span[contains(@class,'ui-input-search__icon_search')]")
    private WebElement search;

    public SearсhResultsPage find(String product) {
        input.sendKeys(product);
        search.click();
        return pageManager.getSearсhResultsPage();
    }
}
