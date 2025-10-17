package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lot {
    private String lotId;
    private String supplierId;
    private String lotCode;
    private Date receivedDate;
    private String description;

    public Lot() {}

    public Lot(String lotId, String supplierId, String lotCode) {
        this.lotId = lotId;
        this.supplierId = supplierId;
        this.lotCode = lotCode;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // ✅ In ra thông tin đẹp hơn
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = (receivedDate != null) ? sdf.format(receivedDate) : "null";

        return "Lot{" +
                "lotId='" + lotId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", lotCode='" + lotCode + '\'' +
                ", receivedDate=" + dateStr +
                ", description='" + description + '\'' +
                '}';
    }
}
