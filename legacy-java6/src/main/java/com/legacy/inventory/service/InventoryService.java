package com.legacy.inventory.service;

import com.legacy.inventory.model.Product;
import com.legacy.inventory.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Inventory stock operations.
 */
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean increaseStock(int productId, int amount) {
        if (amount <= 0) {
            System.out.println("Increase amount must be > 0.");
            return false;
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            System.out.println("Product not found for id=" + productId);
            return false;
        }

        product.increaseQuantity(amount);
        productRepository.save(product);
        return true;
    }

    public boolean decreaseStock(int productId, int amount) {
        if (amount <= 0) {
            System.out.println("Decrease amount must be > 0.");
            return false;
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            System.out.println("Product not found for id=" + productId);
            return false;
        }

        boolean ok = product.decreaseQuantity(amount);
        if (!ok) {
            System.out.println("Insufficient stock for product id=" + productId);
            return false;
        }

        productRepository.save(product);
        return true;
    }

    public boolean hasSufficientStock(int productId, int requestedQuantity) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            return false;
        }
        return product.getQuantity() >= requestedQuantity;
    }

    public List<Product> findLowStockProducts(int threshold) {
        List<Product> all = productRepository.findAll();
        List<Product> low = new ArrayList<Product>();

        for (int i = 0; i < all.size(); i++) {
            Product p = all.get(i);
            if (p.getQuantity() <= threshold) {
                low.add(p);
            }
        }

        Collections.sort(low, new Comparator<Product>() {
            public int compare(Product first, Product second) {
                if (first.getQuantity() < second.getQuantity()) {
                    return -1;
                }
                if (first.getQuantity() > second.getQuantity()) {
                    return 1;
                }
                return first.getName().compareToIgnoreCase(second.getName());
            }
        });

        return low;
    }
}
