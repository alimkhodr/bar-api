package com.bar.model;

public class Waiter extends Person {

    private String shift;

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getRole() {
        return "WAITER";
    }

    @Override
    public void validate() {
        super.validate();
        check(hasText(shift), "shift is required");
    }
}
