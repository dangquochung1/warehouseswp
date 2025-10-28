package model;

import java.sql.Date;

public class RackLotStock {
    private String racklotId;
    private String rackId;
    private String lotdetailId;
    private int quantity;
    private Date expiryDate;
    private String aisleId;
    private String aisleName;
    private String warehouseId;

    // Getters and Setters
    public String getRacklotId() { return racklotId; }
    public void setRacklotId(String racklotId) { this.racklotId = racklotId; }

    public String getRackId() { return rackId; }
    public void setRackId(String rackId) { this.rackId = rackId; }

    public String getLotdetailId() { return lotdetailId; }
    public void setLotdetailId(String lotdetailId) { this.lotdetailId = lotdetailId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getAisleId() { return aisleId; }
    public void setAisleId(String aisleId) { this.aisleId = aisleId; }

    public String getAisleName() { return aisleName; }
    public void setAisleName(String aisleName) { this.aisleName = aisleName; }

    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
}