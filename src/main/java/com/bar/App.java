package com.bar;

import com.bar.db.Database;
import com.bar.http.CustomerHandler;
import com.bar.http.DrinkHandler;
import com.bar.http.IngredientHandler;
import com.bar.http.TabHandler;
import com.bar.http.WaiterHandler;
import com.bar.model.Customer;
import com.bar.model.Drink;
import com.bar.model.Ingredient;
import com.bar.model.Waiter;
import com.bar.repository.CustomerRepository;
import com.bar.repository.DrinkRepository;
import com.bar.repository.IngredientRepository;
import com.bar.repository.LinkRepository;
import com.bar.repository.TabRepository;
import com.bar.repository.WaiterRepository;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;
import com.bar.service.TabItemService;
import com.bar.service.TabService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        Database database = new Database("jdbc:h2:./data/bar");
        database.init();

        CrudService<Customer> customers = new CrudService<>(new CustomerRepository(database), "customer");
        CrudService<Waiter> waiters = new CrudService<>(new WaiterRepository(database), "waiter");
        CrudService<Drink> drinks = new CrudService<>(new DrinkRepository(database), "drink");
        CrudService<Ingredient> ingredients = new CrudService<>(new IngredientRepository(database), "ingredient");
        TabService tabs = new TabService(new TabRepository(database), customers, waiters);

        TabItemService tabItems = new TabItemService(
                new LinkRepository(database, "tab_drinks", "tab_id", "drink_id"), tabs, drinks);
        AssociationService<Drink, Ingredient> recipes = new AssociationService<>(
                new LinkRepository(database, "drink_ingredients", "drink_id", "ingredient_id"), drinks, ingredients);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/customers", new CustomerHandler(customers, tabs));
        server.createContext("/waiters", new WaiterHandler(waiters, tabs));
        server.createContext("/tabs", new TabHandler(tabs, tabItems));
        server.createContext("/drinks", new DrinkHandler(drinks, recipes));
        server.createContext("/ingredients", new IngredientHandler(ingredients, recipes));
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();

        System.out.println("Server running on http://localhost:" + port);
    }
}
