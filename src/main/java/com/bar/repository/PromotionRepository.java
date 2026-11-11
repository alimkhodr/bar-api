package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Promotion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PromotionRepository extends JdbcRepository<Promotion> {

    public PromotionRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "promotions";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "discount_percent", "active");
    }

    @Override
    protected List<Object> values(Promotion promotion) {
        return Arrays.asList(promotion.getName(), promotion.getDiscountPercent(), promotion.getActive());
    }

    @Override
    protected Promotion map(ResultSet resultSet) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId(resultSet.getLong("id"));
        promotion.setName(resultSet.getString("name"));
        promotion.setDiscountPercent(resultSet.getInt("discount_percent"));
        promotion.setActive(resultSet.getBoolean("active"));
        return promotion;
    }
}
