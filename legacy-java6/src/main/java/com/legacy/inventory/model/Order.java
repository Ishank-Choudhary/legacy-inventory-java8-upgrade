package com.legacy.inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents customer order.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Customer customer;
    private Date orderDate;
    private String status;
    private List<OrderItem> items;
    private String notes;

    public Order() {
        this.items = new ArrayList<OrderItem>();
        this.orderDate = new Date();
        this.status = "CREATED";
    }

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.orderDate = new Date();
        this.status = "CREATED";
        this.items = new ArrayList<OrderItem>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addItem(OrderItem item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public int getTotalItems() {
        int total = 0;
        for (int i = 0; i < items.size(); i++) {
            total = total + items.get(i).getQuantity();
        }
        return total;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total = total + items.get(i).getLineTotal();
        }
        return total;
    }
    @Override
    public String toString() {
       return "Order{"+
        "id="+id+
        (customer != null ? ", customer=" + customer.getName() : "")+
        ", orderDate="+orderDate+
        ", status='"+status+'\''+
        ", items="+items.size()+
        ", total="+calculateTotalAmount()+
         (notes != null ? ", notes='"+notes+'\'':"") +
        '}';
    }
}
