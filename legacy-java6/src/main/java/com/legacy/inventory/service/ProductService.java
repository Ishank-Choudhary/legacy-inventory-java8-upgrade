package com.legacy.inventory.service;

import com.legacy.inventory.model.Product;
import com.legacy.inventory.repository.ProductRepository;
import com.legacy.inventory.util.ValidationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Product use-cases and validation.
 */
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(String name, String category, double price, int quantity,
                              String description, String supplierName) {
        if (!ValidationUtil.hasText(name)) {
            System.out.println("Product name is required.");
            return null;
        }
        if (!ValidationUtil.hasText(category)) {
            System.out.println("Product category is required.");
            return null;
        }
        if (price < 0) {
            System.out.println("Product price cannot be negative.");
            return null;
        }
        if (quantity < 0) {
            System.out.println("Product quantity cannot be negative.");
            return null;
        }
        if (productRepository.existsByName(name)) {
            System.out.println("Product with name already exists: " + name);
        }

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setSupplierName(supplierName);

        return productRepository.save(product);
    }

    public Product updateProduct(int id, String name, String category, double price, int quantity,
                                 String description, String supplierName) {
        Product existing = productRepository.findById(id);
        if (existing == null) {
            System.out.println("No product found with id=" + id);
            return null;
        }

        if (ValidationUtil.hasText(name)) {
            existing.setName(name);
        }
        if (ValidationUtil.hasText(category)) {
            existing.setCategory(category);
        }
        if (price >= 0) {
            existing.setPrice(price);
        }
        if (quantity >= 0) {
            existing.setQuantity(quantity);
        }
        if (description != null) {
            existing.setDescription(description);
        }
        if (supplierName != null) {
            existing.setSupplierName(supplierName);
        }
        return productRepository.save(existing);
    }

    public boolean deleteProduct(int id) {
        return productRepository.deleteById(id);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameLike(keyword);
    }

    public List<Product> listProductsSortedByPriceAscending() {
        List<Product> products = productRepository.findAll();
        Collections.sort(products, new Comparator<Product>() {
            public int compare(Product left, Product right) {
                if (left.getPrice() < right.getPrice()) {
                    return -1;
                }
                if (left.getPrice() > right.getPrice()) {
                    return 1;
                }
                return left.getName().compareToIgnoreCase(right.getName());
            }
        });
        return products;
    }

    public List<Product> listProductsSortedByPriceDescending() {
        List<Product> products = productRepository.findAll();
        Collections.sort(products, new Comparator<Product>() {
            public int compare(Product left, Product right) {
                if (left.getPrice() > right.getPrice()) {
                    return -1;
                }
                if (left.getPrice() < right.getPrice()) {
                    return 1;
                }
                return left.getName().compareToIgnoreCase(right.getName());
            }
        });
        return products;
    }

    public List<Product> filterByCategory(String category) {
        List<Product> all = productRepository.findAll();
        List<Product> filtered = new ArrayList<Product>();
        if (!ValidationUtil.hasText(category)) {
            return filtered;
        }

        for (int i = 0; i < all.size(); i++) {
            Product p = all.get(i);
            if (p.getCategory() != null && p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}
