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
                Arguments.of("микроволновая печь", "Микроволновая печь Horizont 20MW700-1379CXW белый",
                        "Гарантия", "+ 12 мес.", "Электрический чайник Redmond","Электрочайник Redmond RK-G191",
                        "Без доп. гарантии","В корзине","В корзину")
        );
    }
}
