package org.example.delivery_server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryRequestDTO request) {
        Delivery delivery = deliveryService.createDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Delivery created successfully with ID: " + delivery.getId() +
                        " for product ID: " + delivery.getProductId());
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Delivery Service is UP");
    }
}