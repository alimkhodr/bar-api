package com.bar.repository;

import com.bar.db.Database;
import com.bar.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CustomerRepository extends JdbcRepository<Customer> {

    public CustomerRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "customers";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "email", "phone");
    }

    @Override
    protected List<Object> values(Customer customer) {
        return Arrays.asList(customer.getName(), customer.getEmail(), customer.getPhone());
    }

    @Override
    protected Customer map(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        return customer;
    }
}
