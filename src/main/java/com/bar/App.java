package com.bar;

import com.bar.db.Database;
import com.bar.http.CategoryHandler;
import com.bar.http.CustomerHandler;
import com.bar.http.MenuItemHandler;
import com.bar.http.PromotionHandler;
import com.bar.http.TabHandler;
import com.bar.model.Category;
import com.bar.model.Customer;
import com.bar.model.MenuItem;
import com.bar.model.Promotion;
import com.bar.repository.CategoryRepository;
import com.bar.repository.CustomerRepository;
import com.bar.repository.LinkRepository;
import com.bar.repository.MenuItemRepository;
import com.bar.repository.PromotionRepository;
import com.bar.repository.TabItemRepository;
import com.bar.repository.TabRepository;
import com.bar.service.AssociationService;
import com.bar.service.CrudService;
import com.bar.service.MenuItemService;
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
        CrudService<Category> categories = new CrudService<>(new CategoryRepository(database), "category");
        CrudService<Promotion> promotions = new CrudService<>(new PromotionRepository(database), "promotion");
        MenuItemService items = new MenuItemService(new MenuItemRepository(database), categories);
        TabService tabs = new TabService(new TabRepository(database), customers);

        AssociationService<Promotion, MenuItem> promotionItems = new AssociationService<>(
                new LinkRepository(database, "promotion_items", "promotion_id", "item_id"), promotions, items);
        TabItemService tabItems = new TabItemService(new TabItemRepository(database), tabs, items, promotionItems);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/customers", new CustomerHandler(customers, tabs));
        server.createContext("/tabs", new TabHandler(tabs, tabItems));
        server.createContext("/categories", new CategoryHandler(categories, items));
        server.createContext("/items", new MenuItemHandler(items, promotionItems));
        server.createContext("/promotions", new PromotionHandler(promotions, promotionItems));
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();

        System.out.println("Server running on http://localhost:" + port);
    }
}
