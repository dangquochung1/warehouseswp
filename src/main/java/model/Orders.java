package model;

import java.util.Date;



public class Orders extends OrderDetail {
    private String orderId;
    private String type;            // inbound | outbound | stocktaking
    private String createdBy;       // Manager tạo
    private String assignedTo;      // Staff thực hiện
    private Date createdAt;
    private Date scheduledDate;
    private String scheduleId;
    private String status;          // pending | processing | done | cancelled
    private String note;

    public Orders(String orderdetailId, String orderId, String productId, int quantityExpected, int quantityActual, float price, String note) {
    }
//    public Orders(String string, String rsString, String s, String string1, String rsString1, String s1, String string2, String rsString2, String s2) {}

    public Orders(String orderId, String type, String createdBy, String assignedTo,
                  java.sql.Date createdAt, java.sql.Date scheduledDate,
                  String scheduleId, String status, String note) {
        this.orderId = orderId;
        this.type = type;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.createdAt = createdAt;
        this.scheduledDate = scheduledDate;
        this.scheduleId = scheduleId;
        this.status = status;
        this.note = note;
    }





    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
