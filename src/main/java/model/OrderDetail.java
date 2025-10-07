package model;

public class OrderDetail {
    private String orderDetailId;
    private String orderId;
    private String lotDetailId; //???????
    private String productId;
    private int quantity_expected;
    private int quantity_actual;
    private double price;
    private String note;
    private String productNamebyId;

    public OrderDetail() {}

    public OrderDetail(String orderDetailId, String orderId, String lotDetailId) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.lotDetailId = lotDetailId;
    }

    public OrderDetail(String orderDetailId, String orderId, String productId, int quantity_actual, double price, String note, String productNamebyId) { //Use for outbound
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity_actual = quantity_actual;
        this.price = price;
        this.note = note;
        this.productNamebyId = productNamebyId;
    }

    public int getQuantity_expected() {
        return quantity_expected;
    }

    public void setQuantity_expected(int quantity_expected) {
        this.quantity_expected = quantity_expected;
    }

    public int getQuantity_actual() {
        return quantity_actual;
    }

    public void setQuantity_actual(int quantity_actual) {
        this.quantity_actual = quantity_actual;
    }

    public String getProductNamebyId() {
        return productNamebyId;
    }

    public void setProductNamebyId(String productNamebyId) {
        this.productNamebyId = productNamebyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}