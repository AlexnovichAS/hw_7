package ru.dns.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс товара
 * @author Алехнович Александр
 */
@Data
@NoArgsConstructor
public class Product {
    private int price;
    private int priceGuarantee = 0;
    private String warranty = "Без доп. гарантии";
    private String name;
}
