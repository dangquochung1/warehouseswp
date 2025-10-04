package model;

import java.util.Date;

public class StocktakingSchedule {
    private String scheduleId;
    private Date startTime;
    private Date endTime;
    private String status;   // pending | in_progress | done | cancelled
    private String note;

    public StocktakingSchedule() {}

    public StocktakingSchedule(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
