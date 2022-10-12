package ru.dns.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.dns.data.Product;

import java.util.ArrayList;
import java.util.List;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'additional-warranties-row__radio')]")
    List<WebElement> listProductGuaranteeCheckbox;

    @FindBy(xpath = "//span[contains(@class,'cart-link__price')]")
    WebElement sumProductBasKet;

    @FindBy(xpath = "//div[@class='cart-items__content-container']")
    List<WebElement> listProductsBasket;

    @FindBy(xpath = "//div[@class='cart-tab-total-amount']//span[@class='restore-last-removed']")
    WebElement buttonReturnDeletedItem;

    @FindBy(xpath = "//button[contains(@id,'buy-btn-main')]")
    WebElement goToCheckout;

    private By plusProduct = By.xpath(".//button[contains(@class,'count-buttons__button_plus')]");

    private List<Product> listProducts = new ArrayList<>();

    private List<Product> listProductsSave = new ArrayList<>();

    private int resultPriceProductBasket;

    private int priceProductDelete;

    public BasketPage checkProductGuaranteeCheckbox(String warrantyInBasket) {
        for (int i = 0; i < listProductGuaranteeCheckbox.size(); i++) {
            if (listProductGuaranteeCheckbox.get(i).getText().replaceAll(" ","")
                    .contains(warrantyInBasket.replaceAll(" ",""))) {
                Assertions.assertTrue(listProductGuaranteeCheckbox.get(i).getAttribute("class").contains("checked"));
                break;
            }
        }
        return this;
    }

    public int getResultPriceProductBasket() {
        return Integer.parseInt(sumProductBasKet.getText().replaceAll("\\D", ""));
    }

    public BasketPage checkProductPrice() {
        for (int i = 0; i < listProductsBasket.size(); i++) {
            String text = listProductsBasket.get(i).findElement(By.xpath(".//a[contains(@class,'base-ui-link')]")).getText();
            if (pageManager.getBasePage().getListProducts().stream().anyMatch(x -> x.getName().equalsIgnoreCase(text))) {
                int priceProduct = pageManager.getBasePage().getListProducts().stream().filter(x->x.getName().equalsIgnoreCase(text))
                        .findFirst().get().getPrice();
                int priceWarranty = pageManager.getBasePage().getListProducts().stream().filter(x->x.getName().equalsIgnoreCase(text))
                        .findFirst().get().getPriceWithWarranty();
                Assertions.assertEquals(priceProduct - priceWarranty, Integer.parseInt(listProductsBasket.get(i)
                        .findElement(By.xpath(".//span[contains(@class,'price__current')]")).getText().replaceAll("\\D", "")));
            }
        }
        return this;
    }

    public BasketPage checkSumBasket() {
        int sumPriceProduct = pageManager.getBasePage().getListProducts().stream().mapToInt(Product::getPrice).sum();
        Assertions.assertEquals(sumPriceProduct, Integer.parseInt(sumProductBasKet.getText().replaceAll("\\D", "")));
        return this;
    }

    public List<Product> getListProductsBasket() {
        Product product = new Product();
        for (int i = 0; i < listProductsBasket.size(); i++) {
            String text = listProductsBasket.get(i).findElement(By.xpath(".//a[contains(@class,'base-ui-link')]")).getText();
            if (pageManager.getBasePage().getListProducts().stream().anyMatch(x -> x.getName().equalsIgnoreCase(text))) {
                waitUtilElementToBeVisible(listProductsBasket.get(i).findElement(By.xpath(".//span[contains(@class,'price__current')]")));
                product.setName(listProductsBasket.get(i).findElement(By.xpath(".//a[contains(@class,'base-ui-link')]")).getText());
                product.setPrice(Integer.parseInt(listProductsBasket.get(i).findElement(By.xpath(".//span[contains(@class,'price__current')]"))
                        .getText().replaceAll("\\D", "")));
                listProducts.add(product);
            }
        }
        return listProducts;
    }

    public BasketPage deleteProductFromBasket(String product) {
        for (int i = 0; i < listProductsBasket.size(); i++) {
            if (listProductsBasket.get(i).getText().contains(product)) {
                listProductsBasket.get(i).findElement(By.xpath(".//button[contains(@class, 'remove-button')]")).click();
//                Assertions.assertTrue( listProductsBasket.get(i).findElement(By.xpath(".//button[contains(@class, 'remove-button')]")).getAttribute("class")
//                .contains("disabled"));
                waitUtilElementToBeClickable(goToCheckout);
                break;
            }
        }
        return this;
    }

    public BasketPage checkDeleteProduct(String productName) {
        Assertions.assertFalse(listProductsBasket.stream().anyMatch(x -> x.findElement(By.xpath(".//a[contains(@class,'base-ui-link')]"))
                .getText().equalsIgnoreCase(productName)));
        Product productResult = pageManager.getBasePage().getListProducts().stream().filter(x -> x.getName().equalsIgnoreCase(productName)).findFirst().get();
        pageManager.getBasePage().deleteProduct(productResult);
        return this;
    }

    public BasketPage checkSumProductsSum() {
        int sumPriceProductsBefore = pageManager.getBasePage().getListProducts().stream().mapToInt(Product::getPrice).sum();
        int result = getResultPriceProductBasket();
        System.out.println(sumPriceProductsBefore);
        System.out.println(result);
        Assertions.assertEquals(sumPriceProductsBefore, result);
        return this;
    }

    public BasketPage plusProductInBasket(String product, int countPlus) {
        int count = 0;
        while (count < countPlus) {
            String text = sumProductBasKet.getText();
            for (WebElement webElement : listProductsBasket) {
                if (webElement.findElement(By.xpath(".//a[contains(@class,'base-ui-link')]")).getText().contains(product)) {
                    waitUtilElementToBeClickable(webElement.findElement(plusProduct));
                    scrollToElementActions(webElement.findElement(plusProduct));
                    elementClickJs(webElement.findElement(plusProduct));
                    waitUtilElementToBeVisible(webElement.findElement(plusProduct));
                    waitUtilElementToBeClickable(goToCheckout);
                    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//span[contains(@class,'cart-link__price')]"), text));
                    //Тут должен быть Assert
                    Product productResult = pageManager.getBasePage().getListProducts().stream().filter(x -> x.getName().equalsIgnoreCase(product)).findFirst().get();
                    pageManager.getBasePage().saveListProducts(productResult);
                    count++;
                }
            }
        }
        return this;
    }

    public BasketPage returnADeletedItem() {
        waitUtilElementToBeClickable(buttonReturnDeletedItem).click();
        waitUtilElementToBeClickable(goToCheckout);
        return this;
    }

    public BasketPage checkAddProduct(String productName, String warranty) {
        Assertions.assertTrue(listProductsBasket.stream().anyMatch(x -> x.findElement(By.xpath(".//a[contains(@class,'base-ui-link')]"))
                .getText().contains(productName)));;
        if (listProductsBasket.stream().anyMatch(x -> x.findElement(By.xpath(".//a[contains(@class,'base-ui-link')]")).getText()
                .replaceAll(" ","").contains(productName.replaceAll(" ","")))) {
            Product product = new Product();
            product.setName(productName);
            product.setWarranty(warranty);
            product.setPrice(Integer.parseInt(listProductsBasket.stream().filter(x -> x.findElement(By.xpath(".//a[contains(@class,'base-ui-link')]"))
                            .getText().contains(productName)).findFirst().get().findElement(By.xpath(".//span[contains(@class,'price__current')]"))
                            .getText().replaceAll("\\D", "")));
            pageManager.getBasePage().saveListProducts(product);
        }
        return this;
    }
}
