package ru.dns.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс товара
 */
@Data
@NoArgsConstructor
public class Product {
    private int price;
    int priceWithWarranty;
    private String warranty = "Без доп. гарантии";
    private String name;
}
