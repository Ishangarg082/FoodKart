package com.fods.model;

import java.math.BigDecimal;

public record MenuItemNameAndPriceDTO(String name, BigDecimal price) {
    @Override
    public String toString() {
        return "MenuItemNameAndPriceDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
