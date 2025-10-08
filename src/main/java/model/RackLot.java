package model;

public class RackLot {

    private String racklotId;
    private String rackId;
    private String lotdetailId;
    private int quantity;

    public RackLot() {
    }

    public RackLot(String racklotId, String rackId, String lotdetailId, int quantity) {
        this.racklotId = racklotId;
        this.rackId = rackId;
        this.lotdetailId = lotdetailId;
        this.quantity = quantity;
    }

    // Getter và Setter
    public String getRacklotId() {
        return racklotId;
    }

    public void setRacklotId(String racklotId) {
        this.racklotId = racklotId;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getLotdetailId() {
        return lotdetailId;
    }

    public void setLotdetailId(String lotdetailId) {
        this.lotdetailId = lotdetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
