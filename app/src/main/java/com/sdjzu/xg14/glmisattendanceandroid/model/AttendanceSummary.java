package com.sdjzu.xg14.glmisattendanceandroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

@Entity
public class AttendanceSummary {
    @Id
    private Long id;
    private String attendanceTime;//考勤时间
    private String attendanceName;//考勤名称
//    private String attendanceManager;//考勤人
    @Transient
    private List<Long> employeeIds;//未出勤的employee的id


    public AttendanceSummary(Long id, String attendanceTime, String attendanceName,
            String attendanceManager) {
        this.id = id;
        this.attendanceTime = attendanceTime;
        this.attendanceName = attendanceName;
//        this.attendanceManager = attendanceManager;
    }


    @Generated(hash = 1258929233)
    public AttendanceSummary(Long id, String attendanceTime,
            String attendanceName) {
        this.id = id;
        this.attendanceTime = attendanceTime;
        this.attendanceName = attendanceName;
    }


    @Generated(hash = 661217013)
    public AttendanceSummary() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAttendanceTime() {
        return this.attendanceTime;
    }


    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }


    public String getAttendanceName() {
        return this.attendanceName;
    }


    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
