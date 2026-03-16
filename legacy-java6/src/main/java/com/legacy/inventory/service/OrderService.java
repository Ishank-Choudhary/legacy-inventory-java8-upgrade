package com.legacy.inventory.service;

import com.legacy.inventory.model.Customer;
import com.legacy.inventory.model.Order;
import com.legacy.inventory.model.OrderItem;
import com.legacy.inventory.model.Product;
import com.legacy.inventory.repository.OrderRepository;
import com.legacy.inventory.repository.ProductRepository;
import com.legacy.inventory.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order business operations.
 */
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    public Order createOrder(Customer customer) {
        if (customer == null || !ValidationUtil.hasText(customer.getName())) {
            System.out.println("Invalid customer.");
            return null;
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");
        order.setItems(new ArrayList<OrderItem>());
        return orderRepository.save(order);
    }

    public boolean addItemToOrder(int orderId, int productId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be > 0.");
            return false;
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            System.out.println("Order not found: " + orderId);
            return false;
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return false;
        }

        if (!inventoryService.hasSufficientStock(productId, quantity)) {
            System.out.println("Insufficient stock for product: " + product.getName());
            return false;
        }

        if (!inventoryService.decreaseStock(productId, quantity)) {
            return false;
        }

        OrderItem item = new OrderItem(product.getId(), product.getName(), quantity, product.getPrice());
        order.addItem(item);
        orderRepository.save(order);
        return true;
    }

    public boolean finalizeOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            return false;
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            System.out.println("Cannot finalize empty order.");
            return false;
        }
        order.setStatus("COMPLETED");
        orderRepository.save(order);
        return true;
    }

    public Order findOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public double calculateOrderTotal(int orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            return 0;
        }
        return order.calculateTotalAmount();
    }

    public int getTotalSoldQuantityForProduct(int productId) {
        List<Order> orders = orderRepository.findAll();
        int total = 0;

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            List<OrderItem> items = order.getItems();
            for (int j = 0; j < items.size(); j++) {
                OrderItem item = items.get(j);
                if (item.getProductId() == productId) {
                    total = total + item.getQuantity();
                }
            }
        }
        return total;
    }
}
