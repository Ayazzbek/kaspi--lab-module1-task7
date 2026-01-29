package org.example.product_server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", url = "${delivery.service.url}")
public interface DeliveryClient {

    @PostMapping("/delivery")
    String createDelivery(@RequestBody DeliveryRequest request);
}