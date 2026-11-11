package com.bar.http;

import com.bar.model.MenuItem;
import com.bar.model.Promotion;
import com.bar.service.AssociationService;
import com.bar.service.MenuItemService;

public class MenuItemHandler extends CrudHandler<MenuItem> {

    private final AssociationService<Promotion, MenuItem> promotions;

    public MenuItemHandler(MenuItemService items, AssociationService<Promotion, MenuItem> promotions) {
        super(items, MenuItem.class);
        this.promotions = promotions;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("promotions")) {
            return get(request, () -> promotions.findOwners(id));
        }
        return super.nested(request, id);
    }
}
