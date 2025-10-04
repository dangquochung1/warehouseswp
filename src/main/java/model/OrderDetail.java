package model;

public class OrderDetail {
    private String orderDetailId;
    private String orderId;
    private String lotDetailId;
    private int quantity;
    private double price;

    public OrderDetail() {}

    public OrderDetail(String orderDetailId, String orderId, String lotDetailId) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.lotDetailId = lotDetailId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLotDetailId() {
        return lotDetailId;
    }

    public void setLotDetailId(String lotDetailId) {
        this.lotDetailId = lotDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
