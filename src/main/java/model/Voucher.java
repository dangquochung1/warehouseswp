package model;

import java.util.Date;

public class Voucher {
    private String voucherId;
    private String type;       // inbound | outbound
    private String orderId;
    private String createdBy;
    private Date createdAt;
    private String note;

    public Voucher() {}

    public Voucher(String voucherId, String type, String orderId) {
        this.voucherId = voucherId;
        this.type = type;
        this.orderId = orderId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
