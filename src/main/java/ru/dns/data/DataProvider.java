package ru.dns.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProvider {

    /**
     * Данные для тестирования
     * @author Алехнович Александр
     */
    public static Stream<Arguments> provideCheckingProductsList(){
        return Stream.of(
                Arguments.of("Телевизор LED Samsung", "55\" (139 см) Телевизор LED Samsung UE55AU7100UXCE черный",
                        "Гарантия", "+ 24 мес.", "Detroit","Detroit","Без доп. гарантии","В корзине","В корзину"),
                Arguments.of("микроволновая печь", "Микроволновая печь Samsung MC32K7055CW/BW белый",
                        "Гарантия", "+ 12 мес.", "Электрочайник Redmond","Электрочайник Redmond RK-M179 бежевый",
                        "Без доп. гарантии","В корзине","В корзину")
        );
    }
}
