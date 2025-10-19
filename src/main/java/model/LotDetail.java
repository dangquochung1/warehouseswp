package model;

public class LotDetail {
    private String lotDetailId;
    private String lotId;
    private String productId;
    private double purchasePrice;
    private int quantityTotal;
    private int remaining;              // cột mới: thay cho status
    private int unarrangedRemaining;    // cột mới: thay cho quantityRemaining

    public LotDetail() {}

    public LotDetail(String lotDetailId, String lotId, String productId, double purchasePrice, int quantityTotal, int remaining, int unarrangedRemaining) {
        this.lotDetailId = lotDetailId;
        this.lotId = lotId;
        this.productId = productId;
        this.purchasePrice = purchasePrice;
        this.quantityTotal = quantityTotal;
        this.remaining = remaining;
        this.unarrangedRemaining = unarrangedRemaining;
    }

    public LotDetail(String lotDetailId, String lotId, String productId) {
        this.lotDetailId = lotDetailId;
        this.lotId = lotId;
        this.productId = productId;
    }

    public String getLotDetailId() {
        return lotDetailId;
    }

    public void setLotDetailId(String lotDetailId) {
        this.lotDetailId = lotDetailId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(int quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getUnarrangedRemaining() {
        return unarrangedRemaining;
    }

    public void setUnarrangedRemaining(int unarrangedRemaining) {
        this.unarrangedRemaining = unarrangedRemaining;
    }

    @Override
    public String toString() {
        return "LotDetail{" +
                "lotDetailId='" + lotDetailId + '\'' +
                ", lotId='" + lotId + '\'' +
                ", productId='" + productId + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", quantityTotal=" + quantityTotal +
                ", remaining=" + remaining +
                ", unarrangedRemaining=" + unarrangedRemaining +
                '}';
    }
}
