package com.bar.http;

import com.bar.exception.MethodNotAllowedException;
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
        if (request.size() == 2 && request.segment(1).equals("total")) {
            return get(request, () -> items.total(id));
        }
        if (!request.segment(1).equals("items")) {
            return super.nested(request, id);
        }
        if (request.size() == 2) {
            return switch (request.method()) {
                case "GET" -> Response.ok(items.findByTab(id));
                case "POST" -> {
                    TabItemRequest body = request.body(TabItemRequest.class);
                    items.add(id, body.itemId(), body.quantity());
                    yield Response.created(items.findByTab(id));
                }
                default -> throw new MethodNotAllowedException(request.method());
            };
        }
        if (request.size() == 3) {
            if (!request.method().equals("DELETE")) {
                throw new MethodNotAllowedException(request.method());
            }
            items.remove(id, request.id(2));
            return Response.noContent();
        }
        return super.nested(request, id);
    }
}
