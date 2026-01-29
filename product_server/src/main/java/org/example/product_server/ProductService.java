package org.example.product_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final DeliveryClient deliveryClient;

    public ProductService(ProductRepository productRepository,
                          DeliveryClient deliveryClient) {
        this.productRepository = productRepository;
        this.deliveryClient = deliveryClient;
    }

    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product);

        // Save product to database
        Product savedProduct = productRepository.save(product);
        log.info("Product saved with ID: {}", savedProduct.getId());

        try {
            // Call delivery service asynchronously
            DeliveryRequest deliveryRequest = new DeliveryRequest(
                    savedProduct.getId(),
                    savedProduct.getAddress()
            );

            String deliveryResponse = deliveryClient.createDelivery(deliveryRequest);
            log.info("Delivery service response: {}", deliveryResponse);

        } catch (Exception e) {
            log.error("Failed to call delivery service for product ID: {}. Error: {}",
                    savedProduct.getId(), e.getMessage());
            // We don't throw exception here to keep product creation successful
            // In real scenario, you might want to implement retry or compensation logic
        }

        return savedProduct;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setAddress(productDetails.getAddress());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }
}