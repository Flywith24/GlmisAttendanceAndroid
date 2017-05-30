package com.sdjzu.xg14.glmisattendanceandroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.AttendanceSummaryDao;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

@Entity
public class AttendanceSummary {
    @Id
    private long id;
    private String attendanceTime;
    private String attendanceName;
    @ToMany(referencedJoinProperty = "attendanceSummaryId")
    private List<Employee> mEmployees;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 108525463)
    private transient AttendanceSummaryDao myDao;
    @Generated(hash = 902930510)
    public AttendanceSummary(long id, String attendanceTime,
            String attendanceName) {
        this.id = id;
        this.attendanceTime = attendanceTime;
        this.attendanceName = attendanceName;
    }
    @Generated(hash = 661217013)
    public AttendanceSummary() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
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
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 800346370)
    public List<Employee> getMEmployees() {
        if (mEmployees == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EmployeeDao targetDao = daoSession.getEmployeeDao();
            List<Employee> mEmployeesNew = targetDao
                    ._queryAttendanceSummary_MEmployees(id);
            synchronized (this) {
                if (mEmployees == null) {
                    mEmployees = mEmployeesNew;
                }
            }
        }
        return mEmployees;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1296600487)
    public synchronized void resetMEmployees() {
        mEmployees = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1661433240)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAttendanceSummaryDao() : null;
    }
    
}
