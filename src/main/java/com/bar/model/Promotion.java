package com.bar.model;

public class Promotion extends BaseEntity {

    private String name;
    private Integer discountPercent;
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int currentDiscount() {
        return Boolean.TRUE.equals(active) ? discountPercent : 0;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(discountPercent != null && discountPercent > 0 && discountPercent <= 100,
                "discountPercent must be between 1 and 100");
        check(active != null, "active is required");
    }
}
