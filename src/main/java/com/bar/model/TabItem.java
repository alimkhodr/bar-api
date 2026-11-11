package com.bar.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TabItem {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private final MenuItem item;
    private final int quantity;
    private final int discountPercent;

    public TabItem(MenuItem item, int quantity, int discountPercent) {
        this.item = item;
        this.quantity = quantity;
        this.discountPercent = discountPercent;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public BigDecimal getTotal() {
        BigDecimal gross = item.getPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal factor = BigDecimal.valueOf(100 - discountPercent).divide(HUNDRED);
        return gross.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
