package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

import java.util.List;

/**
 * Класс описывающий страницу корзины
 * @author Алехнович Александр
 */
public class BasketPage extends BasePage {

    /**
     * @author Алехнович Александр
     * Элемент "Корзина" на странице корзины
     */
    @FindBy(xpath = "//span[contains(@class,'cart-link__price')]")
    private WebElement sumProductBasKet;

    /**
     * @author Алехнович Александр
     * Лист карточек продукта на странице корзины
     */
    @FindBy(xpath = "//div[@class='cart-items__content-container']")
    private List<WebElement> listProductsBasket;

    /**
     * @author Алехнович Александр
     * Кнопка "Вернуть удалённый товар" на странице корзины
     */
    @FindBy(xpath = "//div[@class='cart-tab-total-amount']//span[@class='restore-last-removed']")
    private WebElement buttonReturnDeletedItem;

    /**
     * @author Алехнович Александр
     * Кнопка "Перейти к оформлению" на странице корзины
     */
    @FindBy(xpath = "//button[contains(@id,'buy-btn-main')]")
    private WebElement goToCheckout;

    /**
     * @author Алехнович Александр
     * Для проверки чекбокса с гарантией у товара на странице корзины
     */
    private By productGuaranteeCheckbox = By.xpath(".//div[contains(@class,'additional-warranties-row__radio')]");

    /**
     * @author Алехнович Александр
     * Поиск названия товара в карточке товара на странице корзины
     */
    private By nameProductInBasket = By.xpath(".//a[contains(@class,'base-ui-link')]");

    /**
     * @author Алехнович Александр
     * Поиск кнопки "+" в карточке товара на странице корзины
     */
    private By plusProduct = By.xpath(".//button[contains(@class,'count-buttons__button_plus')]");

    /**
     * @author Алехнович Александр
     * Поиск цены у товара
     */
    private By priceProduct = By.xpath(".//span[contains(@class,'price__current')]");

    /**
     * @author Алехнович Александр
     * Поиск цены у корзины
     */
    private By sumBasket = By.xpath("//span[contains(@class,'cart-link__price')]");

    /**
     * @author Алехнович Александр
     * Поиск кнопки "Удалить" у товара
     */
    private By deleteProduct = By.xpath(".//button[contains(@class, 'remove-button')]");

    /**
     * Проверка чекбокса гарантии на странице корзины
     * @author Алехнович Александр
     * @param nameProduct      - название товара для проверки у него чекбокс
     * @param warrantyInBasket - значение гарантии для проверки
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage checkProductGuaranteeCheckbox(String nameProduct, String warrantyInBasket) {
        for (int i = 0; i < listProductsBasket.size(); i++) {
            if (listProductsBasket.get(i).findElement(nameProductInBasket).getText().equalsIgnoreCase(nameProduct)) {
                WebElement checkBox = listProductsBasket.get(i).findElements(productGuaranteeCheckbox).stream()
                        .filter(x -> x.getText().equalsIgnoreCase(warrantyInBasket)).findFirst().get();
                Assertions.assertTrue(checkBox.getAttribute("class").contains("checked"),
                        "Чекбокс: " + warrantyInBasket + " в корзине у продукта: " + nameProduct + " не выбран");
                return this;
            }
        }
        Assertions.fail("Товар в корзине: " + nameProduct + "не найден");
        return this;
    }

    /**
     * Метод сохраняет цену корзины на странице корзины
     * @author Алехнович Александр
     * @return int - цена в корзине
     */
    public int getResultPriceProductBasket() {
        return getNumberResultReplace(sumProductBasKet);
    }

    /**
     * Метод проверяет цену каждого из товаров
     * @author Алехнович Александр
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage checkProductPrice() {
        pageManager.getBasePage().getListOriginalProducts().forEach(x-> System.out.println(x));
        System.out.println("___________________________________________________");
        pageManager.getBasePage().getListProductsAddInBasket().forEach(x-> System.out.println(x));
        for (int i = 0; i < listProductsBasket.size(); i++) {
            String text = listProductsBasket.get(i).findElement(nameProductInBasket).getText();
            if (pageManager.getBasePage().getListProductsAddInBasket().stream().anyMatch(x -> x.getName().contains(text))) {
                int priceProductResult = pageManager.getBasePage().getListProductsAddInBasket().stream().filter(x -> x.getName().contains(text))
                        .findFirst().get().getPrice();
                int priceWarrantyResult = pageManager.getBasePage().getListProductsAddInBasket().stream().filter(x -> x.getName().contains(text))
                        .findFirst().get().getPriceGuarantee();
                Assertions.assertEquals(priceProductResult - priceWarrantyResult, getNumberResultReplace(listProductsBasket.get(i)
                                .findElement(priceProduct)),
                        "Цена товара в корзине: " + text + " не совпадает");
            }
        }
        return this;
    }

    /**
     * Проверка на равенство суммы корзины и суммы покупок
     *
     * @return BasketPage - т.е. остаемся на этой странице
     * @author Алехнович Александр
     */
    public BasketPage checkSumBasket() {
        int sumPriceProduct = pageManager.getBasePage().getListProductsAddInBasket().stream().mapToInt(Product::getPrice).sum();
        Assertions.assertEquals(sumPriceProduct, getResultPriceProductBasket(), "Сумма покупок не равна сумме корзины");
        return this;
    }

    /**
     * Метод удаляет товар из корзины
     * @author Алехнович Александр
     * @param productName - название товара для удаления
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage deleteProductFromBasket(String productName) {
        for (int i = 0; i < listProductsBasket.size(); i++) {
            if (listProductsBasket.get(i).getText().contains(productName)) {
                listProductsBasket.get(i).findElement(deleteProduct).click();
                waitUtilElementToBeClickable(goToCheckout);
                return this;
            }
        }
        Assertions.fail("Товар: " + productName + "не удален из корзины");
        return this;
    }

    /**
     * Проверка, что продукт удален
     * @author Алехнович Александр
     * @param productName - название удаленного из корзины товара
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage checkDeleteProduct(String productName) {
        Assertions.assertFalse(listProductsBasket.stream().anyMatch(x -> x.findElement(nameProductInBasket)
                .getText().equalsIgnoreCase(productName)), "Товар: " + productName + " не удален из корзины");
        Product productResult = pageManager.getBasePage()
                .getListProductsAddInBasket()
                .stream()
                .filter(x -> x.getName().contains(productName))
                .findFirst()
                .get();
        pageManager.getBasePage().deleteProductAddInBasket(productResult);
        return this;
    }

    /**
     * Метод увеличивает количество товара, путем нажатия на кнопку "+" в карточке товара
     * @author Алехнович Александр
     * @param nameProduct - название товара у которого нужно нажать на кнопку "+"
     * @param countPlus   - количество нажатий на кнопку "+"
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage plusProductInBasket(String nameProduct, int countPlus) {
        int count = 0;
        while (count < countPlus) {
            String text = sumProductBasKet.getText();
            for (WebElement webElement : listProductsBasket) {
                if (webElement.findElement(nameProductInBasket).getText().contains(nameProduct)) {
                    waitUtilElementToBeClickable(webElement.findElement(plusProduct));
                    scrollToElementActions(webElement.findElement(plusProduct));
                    elementClickJs(webElement.findElement(plusProduct));
                    waitUtilElementToBeVisible(webElement.findElement(plusProduct));
                    waitUtilElementToBeClickable(goToCheckout);
                    wait.until(ExpectedConditions.invisibilityOfElementWithText(sumBasket, text));
                    Product productResult = pageManager.getBasePage().getListProductsAddInBasket().stream()
                            .filter(x -> x.getName().equalsIgnoreCase(nameProduct)).findFirst().get();
                    pageManager.getBasePage().saveListProductsAddInBasket(productResult);
                    int sumPriceProduct = pageManager.getBasePage().getListProductsAddInBasket().stream().mapToInt(Product::getPrice).sum();
                    Assertions.assertEquals(sumPriceProduct, getResultPriceProductBasket(),
                            "Количество товара в корзине: " + nameProduct + " не увеличилось");
                    count++;
                } else {
                    Assertions.fail("Товар в корзине: " + nameProduct + " не найден");
                }
            }
        }
        return this;
    }

    /**
     * Метод возвращает удаленный товар
     * @author Алехнович Александр
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage returnDeletedItem() {
        waitUtilElementToBeClickable(buttonReturnDeletedItem).click();
        waitUtilElementToBeClickable(goToCheckout);
        return this;
    }

    /**
     * Проверка, что продукт появился
     * @author Алехнович Александр
     * @param productName - название возвращенного, после удаления из корзины, товара
     * @param warranty    - значение гарантии на товар возвращенного, после удаления из корзины, товара
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage checkAddProduct(String productName, String warranty) {
        Assertions.assertTrue(listProductsBasket.stream().anyMatch(x -> x.findElement(nameProductInBasket)
                .getText().contains(productName)), "Товар: " + productName + "не возвращен в корзину");
        if (listProductsBasket.stream().anyMatch(x -> x.findElement(nameProductInBasket).getText()
                .replaceAll(" ", "").contains(productName.replaceAll(" ", "")))) {
            Product product = new Product();
            product.setName(productName);
            product.setWarranty(warranty);
            product.setPrice(Integer.parseInt(listProductsBasket.stream().filter(x -> x.findElement(nameProductInBasket)
                            .getText().contains(productName)).findFirst().get().findElement(priceProduct)
                    .getText().replaceAll("\\D", "")));
            pageManager.getBasePage().saveListProductsAddInBasket(product);
        }
        return this;
    }
}
