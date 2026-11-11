package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CategoryRepository extends JdbcRepository<Category> {

    public CategoryRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "categories";
    }

    @Override
    protected List<String> columns() {
        return List.of("name");
    }

    @Override
    protected List<Object> values(Category category) {
        return Arrays.asList(category.getName());
    }

    @Override
    protected Category map(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
