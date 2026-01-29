package org.example.product_server;

import jakarta.validation.constraints.NotNull;

public class DeliveryRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Address is required")
    private String address;

    public DeliveryRequest() {}

    public DeliveryRequest(Long productId, String address) {
        this.productId = productId;
        this.address = address;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "productId=" + productId +
                ", address='" + address + '\'' +
                '}';
    }
}