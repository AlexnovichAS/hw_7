package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

import java.util.List;

import static ru.dns.managers.DriverManager.getDriverManager;

/**
 * Класс описывающий страницу карточки продукта
 * @author Алехнович Александр
 */
public class CardProductPage extends BasePage {

    /**
     * @author Алехнович Александр
     * Лист кнопок для выбора дополнительного сервиса на странице карточки продукта
     */
    @FindBy(xpath = "//div[@class= 'additional-sales-tabs__title']")
    List<WebElement> listMenuServices;

    /**
     * @author Алехнович Александр
     * Лист чекбоксов для выбора "Доп.гарантии" на странице карточки продукта
     */
    @FindBy(xpath = "//label[contains(@class,'product-warranty__item')]")
    List<WebElement> listProductWarranty;

    /**
     * @author Алехнович Александр
     * Элемент "Цена товара" на странице карточки продукта
     */
    @FindBy(xpath = "//div[@class= 'product-card-top__buy']//div[contains(@class,'product-buy__price-wrap_interactive')]//div[contains(@class, 'product-buy__price')]")
    WebElement priceProduct;

    /**
     * @author Алехнович Александр
     * Кнопка "Купить" на странице карточки продукта
     */
    @FindBy(xpath = "//div[@class= 'product-card-top__buy']//button[contains(@class,'buy-btn')]")
    WebElement buttonBuy;

    /**
     * @author Алехнович Александр
     * Для клика по чекбоксу у "Доп.гарантии"
     */
    private By checkBox = By.xpath(".//span [@class='product-warranty__period']");

    /**
     * @author Алехнович Александр
     * Значение цены товара без дополнительной гарантии на странице карточки продукта
     */
    private int priceProductWithoutWarranty;

    /**
     * @author Алехнович Александр
     * Значение цены товара с дополнительной гарантии на странице карточки продукта
     */
    private int priceProductWithWarranty;

    /**
     * Проверка перехода на страницу карточки товара
     * @author Алехнович Александр
     * @param nameProduct - название товара для проверки
     * @return CardProductPage - т.е. остаемся на этой странице
     */
    public CardProductPage checkTitle(String nameProduct){
        Assertions.assertEquals(getDriverManager().getDriver().findElement(By.xpath("//h1")).getText(), nameProduct);
        return this;
    }
    /**
     * Метод сохраняет значение цены товара без дополнительной гарантии
     * @author Алехнович Александр
     * @return CardProductPage - т.е. остаемся на этой странице
     */
    public CardProductPage  getResultPriceProductWithoutWarranty() {
        waitUtilElementToBeVisible(priceProduct);
        priceProductWithoutWarranty = Integer.parseInt(priceProduct.getText().replaceAll("\\D", ""));
        return this;
    }

    /**
     * Метод сохраняет значение цены товара с дополнительной гарантии
     * @author Алехнович Александр
     * @return CardProductPage - т.е. остаемся на этой странице
     */
    public CardProductPage getResultPriceProductWithWarranty() {
        waitUtilElementToBeVisible(priceProduct);
        priceProductWithWarranty = Integer.parseInt(priceProduct.getText().substring(0, priceProduct.getText().indexOf("₽"))
                .replaceAll(" ", ""));
        return this;
    }

    /**
     * Метод сохраняет добавленный товар в List, для использования в следующих шагах
     * @author Алехнович Александр
     * @param productName - название товара для сохранения
     * @param warranty - значение гарантии на товар для сохранения
     */
    public void saveProductInBasketPage(String productName, String warranty) {
        Product product = new Product();
        product.setName(productName);
        product.setWarranty(warranty);
        product.setPriceWithWarranty(priceProductWithoutWarranty - priceProductWithWarranty);
        product.setPrice(Integer.parseInt(priceProduct.getText().replaceAll("\\D", "")));
        pageManager.getBasePage().saveListProducts(product);
    }

    /**
     * Метод выбирает дополнительный сервис на странице карточки продукта
     * @author Алехнович Александр
     * @return CardProductPage - т.е. остаемся на этой странице
     */
    public CardProductPage selectionMenuService(String service) {
        for (WebElement product : listMenuServices) {
            if (product.getText().contains(service)) {
                scrollToElementActions(product);
                elementClickJs(product);
                return this;
            }
        }
        Assertions.fail("Дополнительный сервис: " + service + " не найден");
        return this;
    }

    /**
     * Метод выбирает "Доп.гарантию"
     * @author Алехнович Александр
     * @return CardProductPage - т.е. остаемся на этой странице
     */
    public CardProductPage choiceOfGuarantee(String guarantee) {
        for (WebElement product : listProductWarranty) {
            if (product.findElement(checkBox).getText().replaceAll("[a-z]","").equalsIgnoreCase(guarantee)) {
                elementClickJs(product);
                return this;
            }
        }
        Assertions.fail("Дополнительная гарантия: " + guarantee + " не найдена");
        return this;
    }

    /**
     * Метод выбирает "Доп.гарантию"
     * @author Алехнович Александр
     * @param productName - название товара для сохранения
     * @param warranty - значение гарантии на товар для сохранения
     * @param goToBasket - текст для проверки его появления на странице
     * @return StartSearchPage - переходим на страницу карточка товара {@link StartSearchPage}
     */
    public StartSearchPage clickBuyOnProductCard(String productName, String warranty, String goToBasket) {
        scrollToElementActions(buttonBuy);
        waitUtilElementToBeClickable(buttonBuy).click();
        waitUtilElementToBeClickable(buttonBuy);
        wait.until(ExpectedConditions.textToBePresentInElement(buttonBuy, goToBasket));
        saveProductInBasketPage(productName,warranty);
        return pageManager.getStartSearchPage();
    }
}
