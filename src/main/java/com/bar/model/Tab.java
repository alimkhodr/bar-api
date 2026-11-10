package com.bar.model;

public class Tab extends BaseEntity {

    private Integer tableNumber;
    private TabStatus status = TabStatus.OPEN;
    private Long customerId;
    private Long waiterId;

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public TabStatus getStatus() {
        return status;
    }

    public void setStatus(TabStatus status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Long waiterId) {
        this.waiterId = waiterId;
    }

    public boolean acceptsDrinks() {
        return status == TabStatus.OPEN;
    }

    @Override
    public void validate() {
        check(tableNumber != null && tableNumber > 0, "tableNumber must be positive");
        check(status != null, "status is required");
        check(customerId != null, "customerId is required");
        check(waiterId != null, "waiterId is required");
    }
}
