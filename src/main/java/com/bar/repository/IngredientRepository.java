package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class IngredientRepository extends JdbcRepository<Ingredient> {

    public IngredientRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "ingredients";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "unit", "stock_quantity");
    }

    @Override
    protected List<Object> values(Ingredient ingredient) {
        return Arrays.asList(ingredient.getName(), ingredient.getUnit(), ingredient.getStockQuantity());
    }

    @Override
    protected Ingredient map(ResultSet resultSet) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(resultSet.getLong("id"));
        ingredient.setName(resultSet.getString("name"));
        ingredient.setUnit(resultSet.getString("unit"));
        ingredient.setStockQuantity(resultSet.getInt("stock_quantity"));
        return ingredient;
    }
}
