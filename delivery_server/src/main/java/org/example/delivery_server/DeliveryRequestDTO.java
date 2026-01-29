package org.example.delivery_server;

public class DeliveryRequestDTO {
    private Long productId;
    private String address;

    public DeliveryRequestDTO() {}

    public DeliveryRequestDTO(Long productId, String address) {
        this.productId = productId;
        this.address = address;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "DeliveryRequestDTO{" +
                "productId=" + productId +
                ", address='" + address + '\'' +
                '}';
    }
}