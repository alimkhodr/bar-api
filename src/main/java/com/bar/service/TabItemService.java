package com.bar.service;

import com.bar.exception.BadRequestException;
import com.bar.exception.ConflictException;
import com.bar.exception.NotFoundException;
import com.bar.model.MenuItem;
import com.bar.model.Promotion;
import com.bar.model.Tab;
import com.bar.model.TabItem;
import com.bar.model.TabTotal;
import com.bar.repository.TabItemRepository;

import java.math.BigDecimal;
import java.util.List;

public class TabItemService {

    private final TabItemRepository tabItems;
    private final CrudService<Tab> tabs;
    private final CrudService<MenuItem> items;
    private final AssociationService<Promotion, MenuItem> promotions;

    public TabItemService(TabItemRepository tabItems, CrudService<Tab> tabs, CrudService<MenuItem> items,
                          AssociationService<Promotion, MenuItem> promotions) {
        this.tabItems = tabItems;
        this.tabs = tabs;
        this.items = items;
        this.promotions = promotions;
    }

    public void add(long tabId, Long itemId, Integer quantity) {
        if (itemId == null) {
            throw new BadRequestException("itemId is required");
        }
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("quantity must be positive");
        }
        requireOpen(tabId);
        items.findById(itemId);
        tabItems.add(tabId, itemId, quantity);
    }

    public void remove(long tabId, long itemId) {
        requireOpen(tabId);
        if (!tabItems.remove(tabId, itemId)) {
            throw new NotFoundException("item " + itemId + " is not in tab " + tabId);
        }
    }

    public List<TabItem> findByTab(long tabId) {
        tabs.findById(tabId);
        return tabItems.findQuantities(tabId).stream()
                .map(row -> new TabItem(items.findById(row.getKey()), row.getValue(), bestDiscount(row.getKey())))
                .toList();
    }

    public TabTotal total(long tabId) {
        BigDecimal total = findByTab(tabId).stream()
                .map(TabItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);
        return new TabTotal(tabId, total);
    }

    private void requireOpen(long tabId) {
        if (!tabs.findById(tabId).acceptsChanges()) {
            throw new ConflictException("tab " + tabId + " is closed");
        }
    }

    private int bestDiscount(long itemId) {
        return promotions.findOwners(itemId).stream().mapToInt(Promotion::currentDiscount).max().orElse(0);
    }
}
