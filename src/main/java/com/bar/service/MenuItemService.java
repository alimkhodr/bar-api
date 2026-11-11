package com.bar.service;

import com.bar.model.Category;
import com.bar.model.MenuItem;
import com.bar.repository.MenuItemRepository;

import java.util.List;

public class MenuItemService extends CrudService<MenuItem> {

    private final MenuItemRepository items;
    private final CrudService<Category> categories;

    public MenuItemService(MenuItemRepository items, CrudService<Category> categories) {
        super(items, "item");
        this.items = items;
        this.categories = categories;
    }

    public List<MenuItem> findByCategory(long categoryId) {
        categories.findById(categoryId);
        return items.findByCategoryId(categoryId);
    }

    @Override
    protected void checkReferences(MenuItem item) {
        categories.checkExists(item.getCategoryId());
    }
}
