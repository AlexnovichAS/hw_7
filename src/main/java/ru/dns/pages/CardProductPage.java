package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

/**
 * Класс описывающий страницу карточки продукта
 *
 * @author Алехнович Александр
 */
public class CardProductPage extends BasePage {


    /**
     * @author Алехнович Александр
     * Карточка продукта
     */
    @FindBy(xpath = "//div[@class= 'product-card-top product-card-top_full']")
    private WebElement cardProduct;

    /**
     * @author Алехнович Александр
     * Дополнительный сервис на странице карточки продукта
     */
    private By listMenuServices = By.xpath(".//div[@class= 'additional-sales-tabs__title']");

    /**
     * @author Алехнович Александр
     * Название товара
     */
    private By headerPage = By.xpath(".//h1");

    /**
     * @author Алехнович Александр
     * Лист чекбоксов для выбора "Доп.гарантии" на странице карточки продукта
     */
    private By listProductWarranty = By.xpath(".//label[contains(@class,'product-warranty__item')]");

    /**
     * @author Алехнович Александр
     * Элемент "Цена товара" на странице карточки продукта
     */
    private By priceProduct = By.xpath(".//div[contains(@class,'product-buy__price-wrap_interactive')]/div[contains(@class, 'product-buy__price')]");

    /**
     * @author Алехнович Александр
     * Кнопка "Купить" на странице карточки продукта
     */
    private By buttonBuy = By.xpath(".//div[@class= 'product-card-top__buy']//button[contains(@class,'buy-btn')]");

    /**
     * @author Алехнович Александр
     * Для клика по чекбоксу у "Доп.гарантии"
     */
    private By checkBox = By.xpath(".//span [@class='product-warranty__period']");

    /**
     * @author Алехнович Александр
     * Стоимость гарантии
     */
    private By priceGuarantee = By.xpath("./../../span [@class='product-warranty__price']");

    /**
     * @author Алехнович Александр
     * Значение цены товара с дополнительной гарантии на странице карточки продукта
     */
    private int priceProductWithWarranty;

    /**
     * Проверка перехода на страницу карточки товара
     *
     * @param nameProduct - название товара для проверки
     * @return CardProductPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public CardProductPage checkTitle(String nameProduct) {
        Assertions.assertEquals(cardProduct.findElement(headerPage).getText(), nameProduct, "Переход на страницу карточки товара: " + nameProduct + " не осуществлен");
        return this;
    }

    /**
     * Метод сохраняет значение цены товара без дополнительной гарантии
     *
     * @return CardProductPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public CardProductPage saveResultPriceProductWithoutWarranty() {
        waitUtilElementToBeVisible(cardProduct.findElement(priceProduct));
        Product product = saveProduct(cardProduct.findElement(headerPage).getText(),
                new Product().getWarranty(),
                getNumberResultSubstring(cardProduct.findElement(priceProduct)),
                new Product().getPriceGuarantee());
        pageManager.getBasePage().saveListOriginalProducts(product);
        return this;
    }

    /**
     * Метод сохраняет значение цены товара с дополнительной гарантии
     *
     * @return CardProductPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public CardProductPage getResultPriceProductWithWarranty() {
        waitUtilElementToBeVisible(cardProduct.findElement(priceProduct));
        priceProductWithWarranty = getNumberResultSubstring(cardProduct.findElement(priceProduct));
        return this;
    }

    /**
     * Метод сохраняет добавленный товар в List, для использования в следующих шагах
     *
     * @author Алехнович Александр
     */
    public void saveProductInBasketPage(String guarantee) {
        int priceGuaranteeResult = 0;
        for (WebElement products : cardProduct.findElements(listProductWarranty)) {
            if (products.findElement(checkBox).getText().replaceAll("[a-z]", "").equalsIgnoreCase(guarantee)) {
                priceGuaranteeResult = getNumberResultSubstring(products.findElement(checkBox).findElement(priceGuarantee));
            }
        }
        Product product = saveProduct(cardProduct.findElement(headerPage).getText(),
                cardProduct.findElement(checkBox).getText(),
                getNumberResultSubstring(cardProduct.findElement(priceProduct)),
                priceGuaranteeResult);
        pageManager.getBasePage().saveListProductsAddInBasket(product);
    }

    /**
     * Метод выбирает дополнительный сервис на странице карточки продукта
     *
     * @return CardProductPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public CardProductPage selectionMenuService(String service) {
        for (WebElement product : cardProduct.findElements(listMenuServices)) {
            if (product.getText().contains(service)) {
                scrollToElementActions(product);
                elementClickJs(product);
                wait.until(ExpectedConditions.attributeContains(product, "class", "active"));
                return this;
            }
        }
        Assertions.fail("Дополнительный сервис: " + service + " не найден");
        return this;
    }

    /**
     * Метод выбирает "Гарантию"
     *
     * @return CardProductPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public CardProductPage choiceOfGuarantee(String guarantee) {
        int beforePriceIncrease = getNumberResultSubstring(cardProduct.findElement(priceProduct));
        for (WebElement product : cardProduct.findElements(listProductWarranty)) {
            if (product.findElement(checkBox).getText().replaceAll("[a-z]", "").equalsIgnoreCase(guarantee)) {
                elementClickJs(product);
                int priceIncrease = getNumberResultSubstring(product.findElement(checkBox).findElement(priceGuarantee));
                Assertions.assertEquals(beforePriceIncrease + priceIncrease, getNumberResultSubstring(cardProduct.findElement(priceProduct)),
                        "Гарантия: " + guarantee + " не выбрана");
                return this;
            }
        }
        Assertions.fail("Гарантия: " + guarantee + " не найдена");
        return this;
    }

    /**
     * Метод Нажимает на кнопку "Купить"
     *
     * @param goToBasket - текст для проверки его появления на странице
     * @param warranty   - значение гарантии на товар для сохранения
     * @return StartSearchPage - переходим на страницу карточка товара {@link StartSearchPage}
     * @author Алехнович Александр
     */
    public StartSearchPage clickBuyOnProductCard(String warranty, String goToBasket) {
        WebElement element = cardProduct.findElement(buttonBuy);
        scrollToElementActions(element);
        waitUtilElementToBeClickable(element).click();
        waitUtilElementToBeClickable(element);
        wait.until(ExpectedConditions.textToBePresentInElement(element, goToBasket));
        saveProductInBasketPage(warranty);
        return pageManager.getStartSearchPage();
    }
}
