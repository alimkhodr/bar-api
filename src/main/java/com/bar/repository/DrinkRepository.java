package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Drink;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DrinkRepository extends JdbcRepository<Drink> {

    public DrinkRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "drinks";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "price", "alcoholic");
    }

    @Override
    protected List<Object> values(Drink drink) {
        return Arrays.asList(drink.getName(), drink.getPrice(), drink.getAlcoholic());
    }

    @Override
    protected Drink map(ResultSet resultSet) throws SQLException {
        Drink drink = new Drink();
        drink.setId(resultSet.getLong("id"));
        drink.setName(resultSet.getString("name"));
        drink.setPrice(resultSet.getBigDecimal("price"));
        drink.setAlcoholic(resultSet.getBoolean("alcoholic"));
        return drink;
    }
}
