package model;

public class Product {
    private String productId;
    private String img;
    private String name;
    private int quantityAtSystem;
    private int quantityAtReal;
    private double avgPrice;
    private String description;
    private String rackId;
    public Product() {}

    public Product(String productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    public Product(String productId, String name, String rackId) {
        this.productId = productId;
        this.name = name;
        this.rackId = rackId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityAtSystem() {
        return quantityAtSystem;
    }

    public void setQuantityAtSystem(int quantityAtSystem) {
        this.quantityAtSystem = quantityAtSystem;
    }

    public int getQuantityAtReal() {
        return quantityAtReal;
    }

    public void setQuantityAtReal(int quantityAtReal) {
        this.quantityAtReal = quantityAtReal;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRackId() {
        return rackId;
    }
    public void setRackId(String rackId) {
        this.rackId = rackId;
    }
}
