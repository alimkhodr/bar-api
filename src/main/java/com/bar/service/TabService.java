package com.bar.service;

import com.bar.model.Customer;
import com.bar.model.Tab;
import com.bar.repository.TabRepository;

import java.util.List;

public class TabService extends CrudService<Tab> {

    private final TabRepository tabs;
    private final CrudService<Customer> customers;

    public TabService(TabRepository tabs, CrudService<Customer> customers) {
        super(tabs, "tab");
        this.tabs = tabs;
        this.customers = customers;
    }

    public List<Tab> findByCustomer(long customerId) {
        customers.findById(customerId);
        return tabs.findByCustomerId(customerId);
    }

    @Override
    protected void checkReferences(Tab tab) {
        customers.checkExists(tab.getCustomerId());
    }
}
