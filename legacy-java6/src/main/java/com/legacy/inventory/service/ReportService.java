package com.legacy.inventory.service;

import com.legacy.inventory.model.Order;
import com.legacy.inventory.model.OrderItem;
import com.legacy.inventory.model.Product;
import com.legacy.inventory.repository.OrderRepository;
import com.legacy.inventory.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reporting service for products, inventory, and order metrics.
 */
public class ReportService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public ReportService(ProductRepository productRepository,
                         OrderRepository orderRepository,
                         InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    public List<Product> listProductsSortedByPrice(boolean ascending) {
        List<Product> products = productRepository.findAll();
        if (ascending) {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product p1, Product p2) {
                    if (p1.getPrice() < p2.getPrice()) {
                        return -1;
                    }
                    if (p1.getPrice() > p2.getPrice()) {
                        return 1;
                    }
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            });
        } else {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product p1, Product p2) {
                    if (p1.getPrice() > p2.getPrice()) {
                        return -1;
                    }
                    if (p1.getPrice() < p2.getPrice()) {
                        return 1;
                    }
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            });
        }
        return products;
    }

    public List<Product> listLowInventoryProducts(int threshold) {
        return inventoryService.findLowStockProducts(threshold);
    }

    public List<SalesRecord> topSellingProducts(int limit) {
        List<Order> orders = orderRepository.findAll();
        Map<Integer, SalesRecord> productSales = new HashMap<Integer, SalesRecord>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            List<OrderItem> items = order.getItems();
            for (int j = 0; j < items.size(); j++) {
                OrderItem item = items.get(j);
                SalesRecord record = productSales.get(Integer.valueOf(item.getProductId()));
                if (record == null) {
                    record = new SalesRecord(item.getProductId(), item.getProductName(), 0, 0);
                    productSales.put(Integer.valueOf(item.getProductId()), record);
                }
                record.setSoldQuantity(record.getSoldQuantity() + item.getQuantity());
                record.setRevenue(record.getRevenue() + item.getLineTotal());
            }
        }

        List<SalesRecord> records = new ArrayList<SalesRecord>(productSales.values());
        Collections.sort(records, new Comparator<SalesRecord>() {
            public int compare(SalesRecord r1, SalesRecord r2) {
                if (r1.getSoldQuantity() > r2.getSoldQuantity()) {
                    return -1;
                }
                if (r1.getSoldQuantity() < r2.getSoldQuantity()) {
                    return 1;
                }
                if (r1.getRevenue() > r2.getRevenue()) {
                    return -1;
                }
                if (r1.getRevenue() < r2.getRevenue()) {
                    return 1;
                }
                return r1.getProductName().compareToIgnoreCase(r2.getProductName());
            }
        });

        if (limit <= 0 || limit >= records.size()) {
            return records;
        }

        List<SalesRecord> limited = new ArrayList<SalesRecord>();
        for (int i = 0; i < records.size() && i < limit; i++) {
            limited.add(records.get(i));
        }
        return limited;
    }

    public static class SalesRecord {
        private int productId;
        private String productName;
        private int soldQuantity;
        private double revenue;

        public SalesRecord(int productId, String productName, int soldQuantity, double revenue) {
            this.productId = productId;
            this.productName = productName;
            this.soldQuantity = soldQuantity;
            this.revenue = revenue;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getSoldQuantity() {
            return soldQuantity;
        }

        public void setSoldQuantity(int soldQuantity) {
            this.soldQuantity = soldQuantity;
        }

        public double getRevenue() {
            return revenue;
        }

        public void setRevenue(double revenue) {
            this.revenue = revenue;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("SalesRecord{");
            sb.append("productId=").append(productId);
            sb.append(", productName='").append(productName).append('\'');
            sb.append(", soldQuantity=").append(soldQuantity);
            sb.append(", revenue=").append(revenue);
            sb.append('}');
            return sb.toString();
        }
    }
}
