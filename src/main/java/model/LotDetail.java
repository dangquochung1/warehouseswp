package model;

public class LotDetail {
    private String lotDetailId;
    private String lotId;
    private String productId;
    private double purchasePrice;
    private int quantityTotal;
    private int quantityRemaining;

    public LotDetail() {}

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

    public int getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }
    @Override
    public String toString() {
        return "LotDetail{" +
                "lotDetailId='" + lotDetailId + '\'' +
                ", lotId='" + lotId + '\'' +
                ", productId='" + productId + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", quantityTotal=" + quantityTotal +
                ", quantityRemaining=" + quantityRemaining +
                '}';
    }
}
