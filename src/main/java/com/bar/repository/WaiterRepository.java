package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Waiter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class WaiterRepository extends JdbcRepository<Waiter> {

    public WaiterRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "waiters";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "email", "phone", "shift");
    }

    @Override
    protected List<Object> values(Waiter waiter) {
        return Arrays.asList(waiter.getName(), waiter.getEmail(), waiter.getPhone(), waiter.getShift());
    }

    @Override
    protected Waiter map(ResultSet resultSet) throws SQLException {
        Waiter waiter = new Waiter();
        waiter.setId(resultSet.getLong("id"));
        waiter.setName(resultSet.getString("name"));
        waiter.setEmail(resultSet.getString("email"));
        waiter.setPhone(resultSet.getString("phone"));
        waiter.setShift(resultSet.getString("shift"));
        return waiter;
    }
}
