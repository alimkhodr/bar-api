package com.bar.model;

public class Category extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
    }
}
