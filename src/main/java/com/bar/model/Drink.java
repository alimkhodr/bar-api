package com.bar.model;

import java.math.BigDecimal;

public class Drink extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Boolean alcoholic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(Boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(price != null && price.signum() >= 0, "price must be zero or positive");
        check(alcoholic != null, "alcoholic is required");
    }
}
