package com.legacy.inventory.util;

import com.legacy.inventory.model.Customer;
import com.legacy.inventory.model.Order;
import com.legacy.inventory.model.OrderItem;
import com.legacy.inventory.model.Product;
import com.legacy.inventory.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class ConsolePrinterTest {

    public static void main(String[] args) {

        // Test Products
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 50000, 10,
                "Gaming laptop", "Dell"));

        products.add(new Product(2, "Phone", "Electronics", 20000, 5,
                "Smartphone", "Samsung"));

        System.out.println("\n=== PRODUCTS ===");
        ConsolePrinter.printProducts(products);

        // Test Orders
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1, "Laptop", 2, 50000));
        items.add(new OrderItem(2, "Mouse", 1, 500));

        Order order = new Order();
        order.setId(101);
        order.setItems(items);
        Customer customer = new Customer();
        customer.setName("Ishank");
        order.setCustomer(customer);

        // 👉 Make sure customer & date are set if required in your model
        // order.setCustomer(new Customer("Ishank"));
        // order.setOrderDate(LocalDateTime.now());

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        System.out.println("\n=== ORDERS ===");
        ConsolePrinter.printOrders(orders);

        // Test Sales Records
        List<ReportService.SalesRecord> records = new ArrayList<>();
        records.add(new ReportService.SalesRecord(1, "Laptop", 10, 500000));
        records.add(new ReportService.SalesRecord(2, "Phone", 5, 100000));

        System.out.println("\n=== SALES RECORDS ===");
        ConsolePrinter.printSalesRecords(records);

        // Edge Cases
        System.out.println("\n=== EMPTY TEST ===");
        ConsolePrinter.printProducts(new ArrayList<>());

        System.out.println("\n=== NULL TEST ===");
        ConsolePrinter.printProducts(null);
    }
}