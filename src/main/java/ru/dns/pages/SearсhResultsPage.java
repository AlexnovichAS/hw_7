package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

import java.util.List;

public class SearсhResultsPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'catalog-product') and contains (@class,'ui-button-widget')]")
    List<WebElement> listSearchProducts;

    @FindBy(xpath = "//div[contains(@class,'product-buy__price_active')]")
    WebElement priceProduct;

    @FindBy(xpath = "//span[contains(@class,'cart-link__price')]")
    WebElement sumProductBasKet;

    @FindBy(xpath = "//button[contains(@class,'buy-btn')]")
    WebElement buttonBuy;

    @FindBy(xpath = "//a[@data-commerce-target='basket_preview_to_basket']")
    List<WebElement> buttonToBasketAndCheckoutOrder;

    private String buttonForward = "//a[contains(@class,'pagination-widget__page-link_next')]";

    @FindBy(xpath = "//a[contains(@class,'pagination-widget__page-link_next')]")
    WebElement buttonForwardClick;

    private int resultPriceProductBasketInSearch;


    public SearсhResultsPage selectionProduct(String product) {
        long startTime = System.currentTimeMillis();
        long waitTime = 2000000;
        long endTime = startTime + waitTime;
        WebElement text;
        if (isElementExist(By.xpath(buttonForward))) {
            while (isElementExist(By.xpath(buttonForward)) && System.currentTimeMillis() < endTime) {
                for (int i = 0; i < listSearchProducts.size(); i++) {
                    if (listSearchProducts.get(i).findElement(By.xpath("./a/span")).getText().contains(product)) {
                        waitUtilElementToBeClickable(listSearchProducts.get(i));
                        scrollToElementActions(listSearchProducts.get(i));
                        return this;
                    }
                }
                text = listSearchProducts.get(0).findElement(By.xpath("./a/span"));
                scrollToElementActions(buttonForwardClick);
                waitUtilElementToBeClickable(buttonForwardClick).click();
                wait.until(ExpectedConditions.stalenessOf(text));
            }
        } else {
            for (int i = 0; i < listSearchProducts.size(); i++) {
                if (listSearchProducts.get(i).getText().contains(product)) {
                    waitUtilElementToBeClickable(listSearchProducts.get(i));
                    scrollToElementActions(listSearchProducts.get(i));
                    return this;
                }
            }
        }
        Assertions.fail("Товар: " + product + " не был найден");
        return this;
    }


    public CardProductPage selectionProductClick(String product) {
        long startTime = System.currentTimeMillis();
        long waitTime = 2000000;
        long endTime = startTime + waitTime;
        WebElement text;
        if (isElementExist(By.xpath(buttonForward))) {
            while (isElementExist(By.xpath(buttonForward)) && System.currentTimeMillis() < endTime) {
                for (int i = 0; i < listSearchProducts.size(); i++) {
                    if (listSearchProducts.get(i).findElement(By.xpath("./a/span")).getText().contains(product)) {
                        scrollToElementActions(listSearchProducts.get(i));
                        waitUtilElementToBeClickable(listSearchProducts.get(i));
                        elementClickJs(listSearchProducts.get(i).findElement(By.xpath("./a/span")));
                        return pageManager.getCardProductPage();
                    }
                }
                text = listSearchProducts.get(0).findElement(By.xpath("./a/span"));
                scrollToElementActions(buttonForwardClick);
                waitUtilElementToBeClickable(buttonForwardClick).click();
                wait.until(ExpectedConditions.stalenessOf(text));
            }
        } else {
            for (int i = 0; i < listSearchProducts.size(); i++) {
                if (listSearchProducts.get(i).getText().contains(product)) {
                    scrollToElementActions(listSearchProducts.get(i));
                    waitUtilElementToBeClickable(listSearchProducts.get(i));
                    elementClickJs(listSearchProducts.get(i).findElement(By.xpath("./a/span")));
                    return pageManager.getCardProductPage();
                }
            }
        }
        Assertions.fail("Товар: " + product + " не был найден");
        return pageManager.getCardProductPage();
    }

    public void saveProductInSearchPage(String productName, String warranty) {
        Product product = new Product();
        product.setName(productName);
        product.setWarranty(warranty);
        waitUtilElementToBeVisible(priceProduct);
        product.setPrice(Integer.parseInt(priceProduct.getText().substring(0, priceProduct.getText().indexOf("₽")).replaceAll(" ", "")));
        pageManager.getBasePage().saveListProducts(product);
    }

    public SearсhResultsPage clickBuyInSearch(String productName, String warranty,String goToBasket) {
        waitUtilElementToBeClickable(buttonBuy).click();
        waitUtilElementToBeClickable(buttonBuy);
        scrollToElementActions(sumProductBasKet);
        wait.until(ExpectedConditions.attributeContains(buttonBuy, "class", "done"));
        wait.until(ExpectedConditions.textToBePresentInElement(buttonBuy, goToBasket));
        saveProductInSearchPage(productName, warranty);
        return this;
    }

    public int getResultPriceProductBasketInSearch() {
        resultPriceProductBasketInSearch = Integer.parseInt(sumProductBasKet.getText().replaceAll("\\D", ""));
        return resultPriceProductBasketInSearch;
    }

    public SearсhResultsPage checkPriceBasket() {
        int sumPriceProducts = pageManager.getBasePage().getListProducts().stream().mapToInt(Product::getPrice).sum();
        Assertions.assertEquals(sumPriceProducts, getResultPriceProductBasketInSearch(),"Сумма в корзине не равна сумме покупок");
        return this;
    }

    public BasketPage moveToBasket(String goToBasket) {
        for (int i = 0; i < buttonToBasketAndCheckoutOrder.size(); i++) {
            if (buttonToBasketAndCheckoutOrder.get(i).getText().contains(goToBasket)) {
                scrollToElementActions(buttonToBasketAndCheckoutOrder.get(i));
                waitUtilElementToBeClickable(buttonToBasketAndCheckoutOrder.get(i)).click();
                return pageManager.getBasketPage();
            }
        }
        Assertions.fail("Кнопка: " + goToBasket + " не была найдена");
        return pageManager.getBasketPage();
    }
}
