package com.sdjzu.xg14.glmisattendanceandroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */
@Entity
public class Employee {
    @Id
    private long id;
    private String name;
    private String department;
    private long attendanceSummaryId;
    private boolean isAttendant;
    @Generated(hash = 1356239333)
    public Employee(long id, String name, String department,
            long attendanceSummaryId, boolean isAttendant) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.attendanceSummaryId = attendanceSummaryId;
        this.isAttendant = isAttendant;
    }
    @Generated(hash = 202356944)
    public Employee() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public long getAttendanceSummaryId() {
        return this.attendanceSummaryId;
    }
    public void setAttendanceSummaryId(long attendanceSummaryId) {
        this.attendanceSummaryId = attendanceSummaryId;
    }
    public boolean getIsAttendant() {
        return this.isAttendant;
    }
    public void setIsAttendant(boolean isAttendant) {
        this.isAttendant = isAttendant;
    }




}
