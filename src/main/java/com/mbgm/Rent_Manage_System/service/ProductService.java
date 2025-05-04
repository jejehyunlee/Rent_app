package com.mbgm.Rent_Manage_System.service;

import com.mbgm.Rent_Manage_System.entity.Product;
import com.mbgm.Rent_Manage_System.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final AuditorAware<String> auditorAware;


    public List<Product> getAllProducts() {
        return productRepository.findAllNotDeleted();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product updatedProduct) {
        productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setSpecification(updatedProduct.getSpecification());
            product.setStatus(updatedProduct.getStatus());
            product.setDailyRate(updatedProduct.getDailyRate());
            return productRepository.save(product); // ✅ ini update
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).map(product -> {
            product.setDeleted(true);
            product.setDeletedAt(LocalDateTime.now());

            // Ambil user dari auditor
            String currentUser = auditorAware.getCurrentAuditor().orElse("ADMIN");
            product.setDeletedBy(currentUser);

            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}

