package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Tab;
import com.bar.model.TabStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TabRepository extends JdbcRepository<Tab> {

    public TabRepository(Database database) {
        super(database);
    }

    public List<Tab> findByCustomerId(long customerId) {
        return findBy("customer_id", customerId);
    }

    @Override
    protected String table() {
        return "tabs";
    }

    @Override
    protected List<String> columns() {
        return List.of("table_number", "status", "waiter", "customer_id");
    }

    @Override
    protected List<Object> values(Tab tab) {
        return Arrays.asList(tab.getTableNumber(), tab.getStatus().name(), tab.getWaiter(), tab.getCustomerId());
    }

    @Override
    protected Tab map(ResultSet resultSet) throws SQLException {
        Tab tab = new Tab();
        tab.setId(resultSet.getLong("id"));
        tab.setTableNumber(resultSet.getInt("table_number"));
        tab.setStatus(TabStatus.valueOf(resultSet.getString("status")));
        tab.setWaiter(resultSet.getString("waiter"));
        tab.setCustomerId(resultSet.getLong("customer_id"));
        return tab;
    }
}
