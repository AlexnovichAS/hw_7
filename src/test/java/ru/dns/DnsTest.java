package ru.dns;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class DnsTest extends BaseTests {

    @ParameterizedTest(name = "Проверка работы интернет магазина ДНС: {arguments}")
    @MethodSource("ru.dns.data.DataProvider#provideCheckingProductsList")
    public void testAddingGoodsToCartNew(String productOne, String nameProductOne, String menuService, String serviceOptionOne,
                                         String productTwo, String nameProductTwo, String serviceOptionTwo, String text, String menuTotal) {
        app.getStartSearchPage()
                .find(productOne)
                .selectionProductClick(nameProductOne)
                .checkTitle(nameProductOne)
                .getResultPriceProductWithWarranty()
                .selectionMenuService(menuService)
                .choiceOfGuarantee(serviceOptionOne)
                .getResultPriceProductWithoutWarranty()
                .clickBuyOnProductCard(nameProductOne, serviceOptionOne, text)
                .find(productTwo)
                .selectionProduct(nameProductTwo, serviceOptionTwo, text)
                .checkPriceBasket()
                .moveToBasketAndCheckoutOrder(menuTotal)
                .checkProductGuaranteeCheckbox(nameProductOne, serviceOptionOne)
                .checkProductPrice()
                .checkSumBasket()
                .deleteProductFromBasket(nameProductTwo)
                .checkDeleteProduct(nameProductTwo)
                .checkSumBasket()
                .plusProductInBasket(nameProductOne, 2)
                .checkSumBasket()
                .returnDeletedItem()
                .checkAddProduct(productTwo, serviceOptionTwo)
                .checkSumBasket();
    }
}
