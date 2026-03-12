package com.legacy.inventory.repository;

import com.legacy.inventory.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory product repository.
 */
public class ProductRepository {

    private final List<Product> products;
    private int currentId;

    public ProductRepository() {
        this.products = new ArrayList<Product>();
        this.currentId = 1;
    }

    public synchronized Product save(Product product) {
        if (product == null) {
            return null;
        }
        if (product.getId() <= 0) {
            product.setId(nextId());
            products.add(product);
            return product;
        }

        Product existing = findById(product.getId());
        if (existing == null) {
            products.add(product);
        } else {
            existing.setName(product.getName());
            existing.setCategory(product.getCategory());
            existing.setPrice(product.getPrice());
            existing.setQuantity(product.getQuantity());
            existing.setDescription(product.getDescription());
            existing.setSupplierName(product.getSupplierName());
            product = existing;
        }
        return product;
    }

    public synchronized boolean deleteById(int id) {
        Product p = findById(id);
        if (p == null) {
            return false;
        }
        return products.remove(p);
    }

    public synchronized Product findById(int id) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public synchronized List<Product> findAll() {
        return new ArrayList<Product>(products);
    }

    public synchronized List<Product> findByNameLike(String keyword) {
        List<Product> matches = new ArrayList<Product>();
        if (keyword == null) {
            return matches;
        }
        String lowered = keyword.toLowerCase();
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getName() != null && p.getName().toLowerCase().indexOf(lowered) >= 0) {
                matches.add(p);
            }
        }
        return matches;
    }

    public synchronized boolean existsByName(String name) {
        if (name == null) {
            return false;
        }
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (name.equalsIgnoreCase(p.getName())) {
                return true;
            }
        }
        return false;
    }

    public synchronized int count() {
        return products.size();
    }

    private int nextId() {
        int id = currentId;
        currentId = currentId + 1;
        return id;
    }
}
