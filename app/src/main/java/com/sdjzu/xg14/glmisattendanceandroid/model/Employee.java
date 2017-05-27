package com.sdjzu.xg14.glmisattendanceandroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
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
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "department")
    private String department;
    private long attendanceSummaryId;

    @Generated(hash = 1690855840)
    public Employee(long id, String name, String department,
            long attendanceSummaryId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.attendanceSummaryId = attendanceSummaryId;
    }

    @Generated(hash = 202356944)
    public Employee() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
}
