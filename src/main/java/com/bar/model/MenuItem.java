package com.bar.model;

import java.math.BigDecimal;

public class MenuItem extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(price != null && price.signum() >= 0, "price must be zero or positive");
        check(categoryId != null, "categoryId is required");
    }
}
