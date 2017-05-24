package com.sdjzu.xg14.glmisattendanceandroid.model;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AttendanceRecord {
    private long id;
    private String attendanceTime;
    private boolean isAttendant;
    private long employeeId;

    public long getId() {
        return id;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public boolean isAttendant() {
        return isAttendant;
    }
}
