package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MenuItemRepository extends JdbcRepository<MenuItem> {

    public MenuItemRepository(Database database) {
        super(database);
    }

    public List<MenuItem> findByCategoryId(long categoryId) {
        return findBy("category_id", categoryId);
    }

    @Override
    protected String table() {
        return "menu_items";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "description", "price", "category_id");
    }

    @Override
    protected List<Object> values(MenuItem item) {
        return Arrays.asList(item.getName(), item.getDescription(), item.getPrice(), item.getCategoryId());
    }

    @Override
    protected MenuItem map(ResultSet resultSet) throws SQLException {
        MenuItem item = new MenuItem();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setDescription(resultSet.getString("description"));
        item.setPrice(resultSet.getBigDecimal("price"));
        item.setCategoryId(resultSet.getLong("category_id"));
        return item;
    }
}
