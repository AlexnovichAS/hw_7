package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

import java.util.List;

/**
 * Класс описывающий страницу результатов поиска
 * @author Алехнович Александр
 */
public class SearсhResultsPage extends BasePage {

    /**
     * @author Алехнович Александр
     * Лист карточек товара на странице результатов поиска
     */
    @FindBy(xpath = "//div[contains(@class, 'catalog-product') and contains (@class,'ui-button-widget')]")
    List<WebElement> listSearchProducts;

    /**
     * @author Алехнович Александр
     * Элемент "Корзина" на странице результатов поиска
     */
    @FindBy(xpath = "//span[contains(@class,'cart-link__price')]")
    WebElement sumProductBasKet;

    /**
     * @author Алехнович Александр
     * Лист элементов "Корзины" на странице результатов поиска
     */
    @FindBy(xpath = "//button[@data-commerce-target='basket_preview_to_basket']")
    List<WebElement> buttonToBasketAndCheckoutOrder;

    /**
     * @author Алехнович Александр
     * Кнопка пролистывания страниц "Вперед" на странице результатов поиска
     */
    @FindBy(xpath = "//a[contains(@class,'pagination-widget__page-link_next')]")
    WebElement buttonForwardClick;

    /**
     * @author Алехнович Александр
     * Xpath цены продукта на странице результатов поиска
     */
    private By priceProduct = By.xpath(".//div[contains(@class,'product-buy__price')]");

    /**
     * @author Алехнович Александр
     * Xpath кнопки "Купить" товара на странице результатов поиска
     */
    private By buttonBuy = By.xpath(".//button[contains(@class,'buy-btn')]");

    /**
     * @author Алехнович Александр
     * Поиск названия товара в карточке товара на странице результатов поиска
     */
    private By nameProductInCard = By.xpath("./a/span");


    /**
     * Поиск товара на странице результатов поиска и добавление в корзину
     * @author Алехнович Александр
     * @param productName - название товара, который ищем в результатах поиска
     * @param warranty - значение гарантии на товар
     * @param goToBasket - текст для проверки его появления на странице
     * @return SearсhResultsPage - т.е. остаемся на этой странице
     */
    public SearсhResultsPage selectionProduct(String productName, String warranty, String goToBasket) {
        long startTime = System.currentTimeMillis();
        long waitTime = 2000000;
        long endTime = startTime + waitTime;
        WebElement text;
        if (isDisplayedElement(buttonForwardClick)) {
            while (isDisplayedElement(buttonForwardClick) && System.currentTimeMillis() < endTime) {
                for (WebElement listSearchProduct : listSearchProducts) {
                    if (listSearchProduct.findElement(nameProductInCard).getText().contains(productName)) {
                        waitUtilElementToBeClickable(listSearchProduct);
                        scrollToElementActions(listSearchProduct);
                        if (isDisplayedUnderElement(listSearchProduct, buttonBuy)) {
                            waitUtilElementToBeClickable(listSearchProduct.findElement(buttonBuy)).click();
                            waitUtilElementToBeClickable(listSearchProduct.findElement(buttonBuy));
                            scrollToElementActions(sumProductBasKet);
                            wait.until(ExpectedConditions.textToBePresentInElement(listSearchProduct.findElement(buttonBuy), goToBasket));
                            saveProductInSearchPage(listSearchProduct, productName, warranty);
                            return this;
                        } else {
                            Assertions.fail("Товара: " + productName + " нет в наличии");
                        }
                    }
                }
                text = listSearchProducts.get(0).findElement(nameProductInCard);
                if (!buttonForwardClick.getAttribute("class").contains("disabled")){
                    scrollToElementActions(buttonForwardClick);
                    waitUtilElementToBeClickable(buttonForwardClick).click();
                    wait.until(ExpectedConditions.stalenessOf(text));
                } else {
                    Assertions.fail("Товар: " + productName + " не был найден");
                }
            }
        } else {
            for (WebElement listSearchProduct : listSearchProducts) {
                if (listSearchProduct.getText().contains(productName)) {
                    waitUtilElementToBeClickable(listSearchProduct);
                    scrollToElementActions(listSearchProduct);
                    if (isDisplayedUnderElement(listSearchProduct, buttonBuy)) {
                        waitUtilElementToBeClickable(listSearchProduct.findElement(buttonBuy)).click();
                        waitUtilElementToBeClickable(listSearchProduct.findElement(buttonBuy));
                        scrollToElementActions(sumProductBasKet);
                        wait.until(ExpectedConditions.textToBePresentInElement(listSearchProduct.findElement(buttonBuy), goToBasket));
                        saveProductInSearchPage(listSearchProduct, productName, warranty);
                        return this;
                    } else {
                        Assertions.fail("Товара: " + productName + " нет в наличии");
                    }
                }
            }
        }
        Assertions.fail("Товар: " + productName + " не был найден");
        return this;
    }

    /**
     * Поиск товара на странице результатов поиска и переход на страницу карточку товара
     * @author Алехнович Александр
     * @param productName - название товара, который ищем в результатах поиска
     * @return CardProductPage - переходим на страницу карточка товара {@link CardProductPage}
     */
    public CardProductPage selectionProductClick(String productName) {
        long startTime = System.currentTimeMillis();
        long waitTime = 2000000;
        long endTime = startTime + waitTime;
        WebElement text;
        if (isDisplayedElement(buttonForwardClick)) {
            while (isDisplayedElement(buttonForwardClick) && System.currentTimeMillis() < endTime) {
                for (WebElement listSearchProduct : listSearchProducts) {
                    if (listSearchProduct.findElement(nameProductInCard).getText().contains(productName)) {
                        scrollToElementActions(listSearchProduct);
                        if (isDisplayedUnderElement(listSearchProduct, buttonBuy)) {
                            waitUtilElementToBeClickable(listSearchProduct);
                            elementClickJs(listSearchProduct.findElement(nameProductInCard));
                            return pageManager.getCardProductPage();
                        } else {
                            Assertions.fail("Товара: " + productName + " нет в наличии");
                        }
                    }
                }
                text = listSearchProducts.get(0).findElement(nameProductInCard);
                if (!buttonForwardClick.getAttribute("class").contains("disabled")) {
                    scrollToElementActions(buttonForwardClick);
                    waitUtilElementToBeClickable(buttonForwardClick).click();
                    wait.until(ExpectedConditions.stalenessOf(text));
                } else {
                    Assertions.fail("Товар: " + productName + " не был найден");
                }
            }
        } else {
            for (WebElement listSearchProduct : listSearchProducts) {
                if (listSearchProduct.getText().contains(productName)) {
                    scrollToElementActions(listSearchProduct);
                    if (isDisplayedUnderElement(listSearchProduct, buttonBuy)) {
                        waitUtilElementToBeClickable(listSearchProduct);
                        elementClickJs(listSearchProduct.findElement(nameProductInCard));
                        return pageManager.getCardProductPage();
                    } else {
                        Assertions.fail("Товара: " + productName + " нет в наличии");
                    }
                }
            }
        }
        Assertions.fail("Товар: " + productName + " не был найден");
        return pageManager.getCardProductPage();
    }

    /**
     * Метод сохраняет добавленный товар в List, для использования в следующих шагах
     * @author Алехнович Александр
     * @param webElement - веб-элемент товара, у которого берем цену для сохранения
     * @param productName - название товара для сохранения
     * @param warranty - значение гарантии на товар для сохранения
     */
    public void saveProductInSearchPage(WebElement webElement, String productName, String warranty) {
        Product product = new Product();
        product.setName(productName);
        product.setWarranty(warranty);
        waitUtilElementToBeVisible(webElement);
        product.setPrice(Integer.parseInt(webElement.findElement(priceProduct).getText()
                .substring(0, webElement.findElement(priceProduct).getText().indexOf("₽"))
                .replaceAll(" ", "")));
        pageManager.getBasePage().saveListProducts(product);
    }
    /**
     * Метод сохраняет цену корзины на странице результатов поиска
     * @author Алехнович Александр
     * @return int - цена в корзине
     */
    public int getResultPriceProductBasketInSearch() {
        return Integer.parseInt(sumProductBasKet.getText().replaceAll("\\D", ""));
    }

    /**
     * Проверка на равенство суммы корзины и суммы покупок
     * @author Алехнович Александр
     * @return SearсhResultsPage - т.е. остаемся на этой странице
     */
    public SearсhResultsPage checkPriceBasket() {
        int sumPriceProducts = pageManager.getBasePage().getListProducts().stream().mapToInt(Product::getPrice).sum();
        Assertions.assertEquals(sumPriceProducts, getResultPriceProductBasketInSearch(), "Сумма в корзине не равна сумме покупок");
        return this;
    }

    /**
     * Метод перехода в корзину или к оформлению заказа
     * @author Алехнович Александр
     * @param goTo - название кнопки для перехода
     * @return BasketPage - переходим на страницу карточка товара {@link BasketPage}
     */

    public BasketPage moveToBasketAndCheckoutOrder(String goTo) {
        for (int i = 0; i < buttonToBasketAndCheckoutOrder.size(); i++) {
            if (buttonToBasketAndCheckoutOrder.get(i).getText().contains(goTo)) {
                scrollToElementActions(buttonToBasketAndCheckoutOrder.get(i));
                waitUtilElementToBeClickable(buttonToBasketAndCheckoutOrder.get(i)).click();
                return pageManager.getBasketPage();
            }
        }
        Assertions.fail("Кнопка: " + goTo + " не была найдена");
        return pageManager.getBasketPage();
    }
}
