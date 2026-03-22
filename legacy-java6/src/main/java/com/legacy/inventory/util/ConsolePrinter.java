package com.legacy.inventory.util;

import com.legacy.inventory.model.Order;
import com.legacy.inventory.model.OrderItem;
import com.legacy.inventory.model.Product;
import com.legacy.inventory.service.ProductService;
import com.legacy.inventory.service.ReportService;

import java.util.List;

/**
 * Utilities to print console menus and tabular outputs.
 */
public final class ConsolePrinter {

    private ConsolePrinter() {
    }

    public static void printMainMenu() {
        printSeparator();
        System.out.println("Legacy Inventory Management System");
        printSeparator();
        System.out.println("1) Product Management");
        System.out.println("2) Inventory Management");
        System.out.println("3) Order Management");
        System.out.println("4) Reports");
        System.out.println("0) Exit");
        printSeparator();
    }

    public static void printProductMenu() {
        printSeparator();
        System.out.println("Product Management");
        printSeparator();
        System.out.println("1) Add Product");
        System.out.println("2) Update Product");
        System.out.println("3) Delete Product");
        System.out.println("4) List Products");
        System.out.println("5) Search Product by Name");
        System.out.println("0) Back");
        printSeparator();
    }

    public static void printInventoryMenu() {
        printSeparator();
        System.out.println("Inventory Management");
        printSeparator();
        System.out.println("1) Increase Stock");
        System.out.println("2) Decrease Stock");
        System.out.println("3) Low Stock Report");
        System.out.println("0) Back");
        printSeparator();
    }

    public static void printOrderMenu() {
        printSeparator();
        System.out.println("Order Management");
        printSeparator();
        System.out.println("1) Create Order");
        System.out.println("2) Add Product to Existing Order");
        System.out.println("3) Finalize Order");
        System.out.println("4) List Orders");
        System.out.println("0) Back");
        printSeparator();
    }

    public static void printReportMenu() {
        printSeparator();
        System.out.println("Reports");
        printSeparator();
        System.out.println("1) Products Sorted by Price (Asc)");
        System.out.println("2) Products Sorted by Price (Desc)");
        System.out.println("3) Top Selling Products");
        System.out.println("4) Low Inventory Products");
        System.out.println("0) Back");
        printSeparator();
    }

    public static void printProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        printSeparator();
        System.out.println("ID | Name | Category | Price | Qty | Supplier");
        printSeparator();

        // conversion
        products.forEach(ConsolePrinter::printProduct); // using printProduct method reference here
        printSeparator();
    }
    private static void printProduct(Product p){
        System.out.println(p.getId() + " | " + p.getName() + " | " + p.getCategory() + " | "
                + p.getPrice() + " | " + p.getQuantity() + " | " + p.getSupplierName());
    }

    public static void printOrders(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        printSeparator();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println("Order #" + order.getId() + " | Customer=" + order.getCustomer().getName()
                    + " | Date=" + DateUtil.format(order.getOrderDate())
                    + " | Status=" + order.getStatus()
                    + " | Total=" + order.calculateTotalAmount());
            List<OrderItem> items = order.getItems();
            for (int j = 0; j < items.size(); j++) {
                OrderItem item = items.get(j);
                System.out.println("   - " + item.getProductName() + " x" + item.getQuantity()
                        + " @" + item.getUnitPrice() + " = " + item.getLineTotal());
            }
        }
        printSeparator();
    }

    public static void printSalesRecords(List<ReportService.SalesRecord> records) {
        if (records == null || records.isEmpty()) {
            System.out.println("No sales records available.");
            return;
        }

        printSeparator();
        System.out.println("ProductId | ProductName | SoldQty | Revenue");
        printSeparator();
        for (int i = 0; i < records.size(); i++) {
            ReportService.SalesRecord r = records.get(i);
            System.out.println(r.getProductId() + " | " + r.getProductName() + " | "
                    + r.getSoldQuantity() + " | " + r.getRevenue());
        }
        printSeparator();
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }
}
