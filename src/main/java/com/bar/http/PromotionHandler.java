package com.bar.http;

import com.bar.model.MenuItem;
import com.bar.model.Promotion;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;

public class PromotionHandler extends CrudHandler<Promotion> {

    private final AssociationService<Promotion, MenuItem> items;

    public PromotionHandler(CrudService<Promotion> promotions, AssociationService<Promotion, MenuItem> items) {
        super(promotions, Promotion.class);
        this.items = items;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.segment(1).equals("items")) {
            return association(request, id, items);
        }
        return super.nested(request, id);
    }
}
