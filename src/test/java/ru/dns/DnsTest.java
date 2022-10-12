package ru.dns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class DnsTest extends BaseTests {

    @ParameterizedTest(name = "Проверка работы интернет магазина ДНС: {arguments}")
    @MethodSource("ru.dns.data.DataProvider#provideCheckingProductsList")
    public void testAddingGoodsToCartNew(String productOne, String nameProductOne, String menuService, String serviceOptionOne,
                                         String productTwo, String nameProductTwo,String serviceOptionTwo, String text, String menuTotal ) {
        app.getStartSearchPage()
                .find(productOne)
                .selectionProductClick(nameProductOne)
                .getResultPriceProductWithWarranty()
                .selectionMenuService(menuService)
                .choiceOfGuarantee(serviceOptionOne)
                .getResultPriceProductWithoutWarranty()
                .clickBuyOnProductCard(nameProductOne, serviceOptionOne)
                .find(productTwo)
                .selectionProduct(nameProductTwo)
                .clickBuyInSearch(productTwo,serviceOptionTwo,text)
                .checkPriceBasket()
                .moveToBasket(menuTotal)
                .checkProductGuaranteeCheckbox(serviceOptionOne)
                .checkProductPrice()
                .checkSumBasket()
                .deleteProductFromBasket(productTwo)
                .checkDeleteProduct(productTwo)
                .checkSumProductsSum()
                .plusProductInBasket(nameProductOne,2)
                .checkSumProductsSum()
                .returnADeletedItem()
                .checkAddProduct(productTwo, serviceOptionTwo)
                .checkSumProductsSum();
    }

    @Test
    public void testAddingGoodsToCartNew1() {
        app.getStartSearchPage()
                .find("Телевизор LED Samsung")
                .selectionProductClick("65\" (163 см) Телевизор LED Samsung UE65AU7100UXCE серый");
    }
}
