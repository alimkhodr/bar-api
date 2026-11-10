package com.bar.http;

import com.bar.model.Waiter;
import com.bar.service.CrudService;
import com.bar.service.TabService;

public class WaiterHandler extends CrudHandler<Waiter> {

    private final TabService tabs;

    public WaiterHandler(CrudService<Waiter> waiters, TabService tabs) {
        super(waiters, Waiter.class);
        this.tabs = tabs;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("tabs")) {
            return get(request, () -> tabs.findByWaiter(id));
        }
        return super.nested(request, id);
    }
}
