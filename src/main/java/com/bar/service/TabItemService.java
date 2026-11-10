package com.bar.service;

import com.bar.exception.ConflictException;
import com.bar.model.Drink;
import com.bar.model.Tab;
import com.bar.repository.LinkRepository;

public class TabItemService extends AssociationService<Tab, Drink> {

    public TabItemService(LinkRepository items, CrudService<Tab> tabs, CrudService<Drink> drinks) {
        super(items, tabs, drinks);
    }

    @Override
    protected void beforeLink(Tab tab, int itemCount) {
        if (!tab.acceptsDrinks()) {
            throw new ConflictException("tab " + tab.getId() + " is closed");
        }
    }
}
