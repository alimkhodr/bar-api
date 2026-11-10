package com.bar.http;

import com.bar.model.Customer;
import com.bar.service.CrudService;
import com.bar.service.TabService;

public class CustomerHandler extends CrudHandler<Customer> {

    private final TabService tabs;

    public CustomerHandler(CrudService<Customer> customers, TabService tabs) {
        super(customers, Customer.class);
        this.tabs = tabs;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("tabs")) {
            return get(request, () -> tabs.findByCustomer(id));
        }
        return super.nested(request, id);
    }
}
