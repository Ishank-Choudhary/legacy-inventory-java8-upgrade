package com.legacy.inventory.repository;

import com.legacy.inventory.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory order repository.
 */
public class OrderRepository {

    private final List<Order> orders;
    private int currentId;

    public OrderRepository() {
        this.orders = new ArrayList<Order>();
        this.currentId = 1;
    }

    public synchronized Order save(Order order) {
        if (order == null) {
            return null;
        }
        if (order.getId() <= 0) {
            order.setId(nextId());
            orders.add(order);
            return order;
        }

        Order existing = findById(order.getId());
        if (existing == null) {
            orders.add(order);
            return order;
        }

        existing.setCustomer(order.getCustomer());
        existing.setOrderDate(order.getOrderDate());
        existing.setStatus(order.getStatus());
        existing.setItems(order.getItems());
        existing.setNotes(order.getNotes());
        return existing;
    }

    public synchronized Order findById(int id) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public synchronized List<Order> findAll() {
        return new ArrayList<Order>(orders);
    }

    public synchronized int count() {
        return orders.size();
    }

    private int nextId() {
        int id = currentId;
        currentId = currentId + 1;
        return id;
    }
}
