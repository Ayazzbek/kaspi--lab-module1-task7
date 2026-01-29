package org.example.delivery_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryService {

    private static final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    public Delivery createDelivery(DeliveryRequestDTO request) {
        log.info("Creating delivery for product ID: {}, address: {}",
                request.getProductId(), request.getAddress());

        Delivery delivery = new Delivery(request.getProductId(), request.getAddress());
        Delivery savedDelivery = deliveryRepository.save(delivery);

        log.info("Delivery created with ID: {}", savedDelivery.getId());

        // Simulate delivery processing
        processDelivery(savedDelivery);

        return savedDelivery;
    }

    private void processDelivery(Delivery delivery) {
        // Simulate async delivery processing
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate processing time
                delivery.setStatus("PROCESSED");
                deliveryRepository.save(delivery);
                log.info("Delivery ID: {} processed successfully", delivery.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                delivery.setStatus("FAILED");
                deliveryRepository.save(delivery);
                log.error("Failed to process delivery ID: {}", delivery.getId());
            }
        }).start();
    }
}