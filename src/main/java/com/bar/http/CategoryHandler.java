package com.bar.http;

import com.bar.model.Category;
import com.bar.service.CrudService;
import com.bar.service.MenuItemService;

public class CategoryHandler extends CrudHandler<Category> {

    private final MenuItemService items;

    public CategoryHandler(CrudService<Category> categories, MenuItemService items) {
        super(categories, Category.class);
        this.items = items;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("items")) {
            return get(request, () -> items.findByCategory(id));
        }
        return super.nested(request, id);
    }
}
