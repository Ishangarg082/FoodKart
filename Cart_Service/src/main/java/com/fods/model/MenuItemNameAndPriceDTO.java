package com.fods.model;

import java.math.BigDecimal;

public record MenuItemNameAndPriceDTO(String name, BigDecimal price, boolean isAvailable, String imageUrl) {
    @Override
    public String toString() {
        return "MenuItemNameAndPriceDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", imageURL=" + imageUrl +
                '}';
    }
}
