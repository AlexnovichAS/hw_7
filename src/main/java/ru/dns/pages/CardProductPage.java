package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.dns.data.Product;

import java.util.ArrayList;
import java.util.List;

public class CardProductPage extends BasePage {

    @FindBy(xpath = "//div[@class= 'additional-sales-tabs__title']")
    List<WebElement> listMenuServices;

    @FindBy(xpath = "//label[contains(@class,'product-warranty__item')]")
    List<WebElement> listProductWarranty;

    @FindBy(xpath = "//div[@class= 'product-card-top__buy']//div[contains(@class,'product-buy__price-wrap_interactive')]//div[contains(@class, 'product-buy__price')]")
    WebElement priceProduct;

    @FindBy(xpath = "//div[@class= 'product-card-top__buy']//button[contains(@class,'buy-btn')]")
    WebElement buttonBuy;

    private List<Product> listProductsSave = new ArrayList<>();

    private int priceProductWithoutWarranty;

    private int priceProductWithWarranty;


    public CardProductPage getResultPriceProductWithWarranty() {
        waitUtilElementToBeVisible(priceProduct);
        priceProductWithWarranty = Integer.parseInt(priceProduct.getText().replaceAll("\\D", ""));
        return this;
    }

    public CardProductPage  getResultPriceProductWithoutWarranty() {
        waitUtilElementToBeVisible(priceProduct);
        priceProductWithoutWarranty = Integer.parseInt(priceProduct.getText().replaceAll("\\D", ""));
        return this;
    }

    public CardProductPage saveProductInBasketPage(String productName, String warranty) {
        Product product = new Product();
        product.setName(productName);
        product.setWarranty(warranty);
        product.setPriceWithWarranty(priceProductWithoutWarranty - priceProductWithWarranty);
        product.setPrice(Integer.parseInt(priceProduct.getText().replaceAll("\\D", "")));
        listProductsSave = pageManager.getBasePage().saveListProducts(product);
        return this;
    }

    public CardProductPage selectionMenuService(String service) {
        for (WebElement product : listMenuServices) {
            if (product.getText().contains(service)) {
                scrollToElementActions(product);
                elementClickJs(product);
                break;
            }
        }
        return this;
    }

    public CardProductPage choiceOfGuarantee(String guarantee) {
        for (WebElement product : listProductWarranty) {
            if (product.findElement(By.xpath(".//span [@class='product-warranty__period']")).getText().replaceAll("[a-z]","").contains(guarantee)) {
//                scrollToElementJs(product);
                elementClickJs(product);
                break;
            }
        }
        return this;
    }

    public StartSearchPage clickBuyOnProductCard(String productName, String warranty) {
        scrollToElementActions(buttonBuy);
        waitUtilElementToBeClickable(buttonBuy).click();
        waitUtilElementToBeClickable(buttonBuy);
        saveProductInBasketPage(productName,warranty);
        return pageManager.getStartSearchPage();
    }
}
