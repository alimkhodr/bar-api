package com.bar.model;

public class Ingredient extends BaseEntity {

    private String name;
    private String unit;
    private Integer stockQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(hasText(unit), "unit is required");
        check(stockQuantity != null && stockQuantity >= 0, "stockQuantity must be zero or positive");
    }
}
