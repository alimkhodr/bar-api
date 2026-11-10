package com.bar.http;

import com.bar.model.Tab;
import com.bar.service.TabItemService;
import com.bar.service.TabService;

public class TabHandler extends CrudHandler<Tab> {

    private final TabItemService items;

    public TabHandler(TabService tabs, TabItemService items) {
        super(tabs, Tab.class);
        this.items = items;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.segment(1).equals("drinks")) {
            return association(request, id, items);
        }
        return super.nested(request, id);
    }
}
