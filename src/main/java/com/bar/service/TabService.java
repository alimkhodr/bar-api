package com.bar.service;

import com.bar.model.Customer;
import com.bar.model.Tab;
import com.bar.model.Waiter;
import com.bar.repository.TabRepository;

import java.util.List;

public class TabService extends CrudService<Tab> {

    private final TabRepository tabs;
    private final CrudService<Customer> customers;
    private final CrudService<Waiter> waiters;

    public TabService(TabRepository tabs, CrudService<Customer> customers, CrudService<Waiter> waiters) {
        super(tabs, "tab");
        this.tabs = tabs;
        this.customers = customers;
        this.waiters = waiters;
    }

    public List<Tab> findByCustomer(long customerId) {
        customers.findById(customerId);
        return tabs.findByCustomerId(customerId);
    }

    public List<Tab> findByWaiter(long waiterId) {
        waiters.findById(waiterId);
        return tabs.findByWaiterId(waiterId);
    }

    @Override
    protected void checkReferences(Tab tab) {
        customers.checkExists(tab.getCustomerId());
        waiters.checkExists(tab.getWaiterId());
    }
}
