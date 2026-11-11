package com.bar.repository;

import com.bar.db.Database;

import java.util.List;
import java.util.Map;

public class TabItemRepository {

    private final Database database;

    public TabItemRepository(Database database) {
        this.database = database;
    }

    public void add(long tabId, long itemId, int quantity) {
        int updated = database.update(
                "UPDATE tab_items SET quantity = quantity + ? WHERE tab_id = ? AND item_id = ?", quantity, tabId, itemId);
        if (updated == 0) {
            database.update("INSERT INTO tab_items (tab_id, item_id, quantity) VALUES (?, ?, ?)", tabId, itemId, quantity);
        }
    }

    public boolean remove(long tabId, long itemId) {
        return database.update("DELETE FROM tab_items WHERE tab_id = ? AND item_id = ?", tabId, itemId) > 0;
    }

    public List<Map.Entry<Long, Integer>> findQuantities(long tabId) {
        return database.query("SELECT item_id, quantity FROM tab_items WHERE tab_id = ? ORDER BY item_id",
                resultSet -> Map.entry(resultSet.getLong("item_id"), resultSet.getInt("quantity")), tabId);
    }
}
