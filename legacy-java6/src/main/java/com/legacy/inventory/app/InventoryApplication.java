package com.legacy.inventory.app;

import com.legacy.inventory.model.Customer;
import com.legacy.inventory.model.Order;
import com.legacy.inventory.model.Product;
import com.legacy.inventory.repository.OrderRepository;
import com.legacy.inventory.repository.ProductRepository;
import com.legacy.inventory.service.InventoryService;
import com.legacy.inventory.service.OrderService;
import com.legacy.inventory.service.ProductService;
import com.legacy.inventory.service.ReportService;
import com.legacy.inventory.util.ConsolePrinter;
import com.legacy.inventory.util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Console-driven legacy inventory application.
 */
public class InventoryApplication {

    private final Scanner scanner;

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final ReportService reportService;

    public InventoryApplication() {
        this.scanner = new Scanner(System.in);

        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();

        this.productService = new ProductService(productRepository);
        this.inventoryService = new InventoryService(productRepository);
        this.orderService = new OrderService(orderRepository, productRepository, inventoryService);
        this.reportService = new ReportService(productRepository, orderRepository, inventoryService);

        seedData();
    }

    public static void main(String[] args) {
        InventoryApplication application = new InventoryApplication();
        application.start();
    }

    public void start() {
        boolean running = true;

        while (running) {
            ConsolePrinter.printMainMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    productManagement();
                    break;
                case 2:
                    inventoryManagement();
                    break;
                case 3:
                    orderManagement();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 0:
                    running = false;
                    ConsolePrinter.printMessage("Exiting application. Goodbye.");
                    break;
                default:
                    ConsolePrinter.printMessage("Unknown option.");
                    break;
            }
        }
    }

    private void productManagement() {
        boolean back = false;

        while (!back) {
            ConsolePrinter.printProductMenu();
            int choice = readInt("Select product action: ");

            switch (choice) {
                case 1:
                    addProductFlow();
                    break;
                case 2:
                    updateProductFlow();
                    break;
                case 3:
                    deleteProductFlow();
                    break;
                case 4:
                    listProductsFlow();
                    break;
                case 5:
                    searchProductsFlow();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    ConsolePrinter.printMessage("Invalid option.");
                    break;
            }
        }
    }

    private void addProductFlow() {
        String name = readText("Name: ");
        String category = readText("Category: ");
        double price = readDouble("Price: ");
        int quantity = readInt("Quantity: ");
        String description = readText("Description: ");
        String supplier = readText("Supplier: ");

        Product product = productService.addProduct(name, category, price, quantity, description, supplier);
        if (product != null) {
            ConsolePrinter.printMessage("Added product id=" + product.getId());
        }
    }

    private void updateProductFlow() {
        int id = readInt("Product ID to update: ");
        Product existing = productService.getProductById(id);
        if (existing == null) {
            ConsolePrinter.printMessage("Product not found.");
            return;
        }

        ConsolePrinter.printMessage("Leave text empty to keep current value.");
        String name = readTextAllowEmpty("Name (current=" + existing.getName() + "): ");
        String category = readTextAllowEmpty("Category (current=" + existing.getCategory() + "): ");
        String priceText = readTextAllowEmpty("Price (current=" + existing.getPrice() + "): ");
        String qtyText = readTextAllowEmpty("Quantity (current=" + existing.getQuantity() + "): ");
        String desc = readTextAllowEmpty("Description (current=" + existing.getDescription() + "): ");
        String supplier = readTextAllowEmpty("Supplier (current=" + existing.getSupplierName() + "): ");

        double price = existing.getPrice();
        int quantity = existing.getQuantity();

        if (ValidationUtil.hasText(priceText)) {
            price = parseDoubleOrDefault(priceText, existing.getPrice());
        }
        if (ValidationUtil.hasText(qtyText)) {
            quantity = parseIntOrDefault(qtyText, existing.getQuantity());
        }

        Product updated = productService.updateProduct(
                id,
                ValidationUtil.hasText(name) ? name : existing.getName(),
                ValidationUtil.hasText(category) ? category : existing.getCategory(),
                price,
                quantity,
                ValidationUtil.hasText(desc) ? desc : existing.getDescription(),
                ValidationUtil.hasText(supplier) ? supplier : existing.getSupplierName()
        );

        if (updated != null) {
            ConsolePrinter.printMessage("Updated product id=" + updated.getId());
        }
    }

    private void deleteProductFlow() {
        int id = readInt("Product ID to delete: ");
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            ConsolePrinter.printMessage("Product removed.");
        } else {
            ConsolePrinter.printMessage("Product not found.");
        }
    }

    private void listProductsFlow() {
        List<Product> products = productService.listProducts();
        ConsolePrinter.printProducts(products);
    }

    private void searchProductsFlow() {
        String keyword = readText("Enter product name keyword: ");
        List<Product> products = productService.searchByName(keyword);
        ConsolePrinter.printProducts(products);
    }

    private void inventoryManagement() {
        boolean back = false;

        while (!back) {
            ConsolePrinter.printInventoryMenu();
            int choice = readInt("Select inventory action: ");

            switch (choice) {
                case 1:
                    increaseStockFlow();
                    break;
                case 2:
                    decreaseStockFlow();
                    break;
                case 3:
                    lowStockFlow();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    ConsolePrinter.printMessage("Invalid option.");
                    break;
            }
        }
    }

    private void increaseStockFlow() {
        int productId = readInt("Product ID: ");
        int amount = readInt("Increase amount: ");
        boolean ok = inventoryService.increaseStock(productId, amount);
        if (ok) {
            ConsolePrinter.printMessage("Stock increased.");
        }
    }

    private void decreaseStockFlow() {
        int productId = readInt("Product ID: ");
        int amount = readInt("Decrease amount: ");
        boolean ok = inventoryService.decreaseStock(productId, amount);
        if (ok) {
            ConsolePrinter.printMessage("Stock decreased.");
        }
    }

    private void lowStockFlow() {
        int threshold = readInt("Low stock threshold: ");
        List<Product> lowStock = inventoryService.findLowStockProducts(threshold);
        ConsolePrinter.printProducts(lowStock);
    }

    private void orderManagement() {
        boolean back = false;

        while (!back) {
            ConsolePrinter.printOrderMenu();
            int choice = readInt("Select order action: ");

            switch (choice) {
                case 1:
                    createOrderFlow();
                    break;
                case 2:
                    addProductToOrderFlow();
                    break;
                case 3:
                    finalizeOrderFlow();
                    break;
                case 4:
                    listOrdersFlow();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    ConsolePrinter.printMessage("Invalid option.");
                    break;
            }
        }
    }

    private void createOrderFlow() {
        ConsolePrinter.printMessage("Enter customer details");
        int customerId = readInt("Customer ID: ");
        String name = readText("Customer Name: ");
        String email = readText("Customer Email: ");
        String phone = readText("Customer Phone: ");
        String address = readText("Customer Address: ");

        Customer customer = new Customer(customerId, name, email, phone, address);
        Order order = orderService.createOrder(customer);
        if (order != null) {
            ConsolePrinter.printMessage("Order created. ID=" + order.getId());
        }
    }

    private void addProductToOrderFlow() {
        int orderId = readInt("Order ID: ");
        int productId = readInt("Product ID: ");
        int quantity = readInt("Quantity: ");

        boolean added = orderService.addItemToOrder(orderId, productId, quantity);
        if (added) {
            double total = orderService.calculateOrderTotal(orderId);
            ConsolePrinter.printMessage("Item added. Order total now=" + total);
        }
    }

    private void finalizeOrderFlow() {
        int orderId = readInt("Order ID to finalize: ");
        boolean ok = orderService.finalizeOrder(orderId);
        if (ok) {
            ConsolePrinter.printMessage("Order finalized.");
        } else {
            ConsolePrinter.printMessage("Failed to finalize order.");
        }
    }

    private void listOrdersFlow() {
        List<Order> orders = orderService.listOrders();
        ConsolePrinter.printOrders(orders);
    }

    private void reportsMenu() {
        boolean back = false;

        while (!back) {
            ConsolePrinter.printReportMenu();
            int choice = readInt("Select report action: ");

            switch (choice) {
                case 1:
                    productsByPriceAscFlow();
                    break;
                case 2:
                    productsByPriceDescFlow();
                    break;
                case 3:
                    topSellingFlow();
                    break;
                case 4:
                    reportLowInventoryFlow();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    ConsolePrinter.printMessage("Invalid option.");
                    break;
            }
        }
    }

    private void productsByPriceAscFlow() {
        List<Product> products = reportService.listProductsSortedByPrice(true);
        ConsolePrinter.printProducts(products);
    }

    private void productsByPriceDescFlow() {
        List<Product> products = reportService.listProductsSortedByPrice(false);
        ConsolePrinter.printProducts(products);
    }

    private void topSellingFlow() {
        int limit = readInt("How many top products to show? ");
        List<ReportService.SalesRecord> records = reportService.topSellingProducts(limit);
        ConsolePrinter.printSalesRecords(records);
    }

    private void reportLowInventoryFlow() {
        int threshold = readInt("Low inventory threshold: ");
        List<Product> products = reportService.listLowInventoryProducts(threshold);
        ConsolePrinter.printProducts(products);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ConsolePrinter.printMessage("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                ConsolePrinter.printMessage("Please enter a valid number.");
            }
        }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (ValidationUtil.hasText(input)) {
                return input.trim();
            }
            ConsolePrinter.printMessage("Value is required.");
        }
    }

    private String readTextAllowEmpty(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void seedData() {
        productService.addProduct("Laptop Pro 15", "Electronics", 1200.0, 10,
                "High performance laptop", "Global Tech Ltd");
        productService.addProduct("Wireless Mouse", "Electronics", 25.5, 150,
                "Ergonomic office mouse", "Peripherals Inc");
        productService.addProduct("Office Chair", "Furniture", 230.0, 25,
                "Adjustable office chair", "Comfort Seating Co");
        productService.addProduct("Notebook Pack", "Stationery", 12.75, 200,
                "Pack of 5 ruled notebooks", "Paper Goods LLC");
    }
}
