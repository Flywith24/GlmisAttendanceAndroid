package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.IBaseView;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;
import java.util.Map;

/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface IAddAttendanceView extends IBaseView {
    void addAttendanceSucceed(String str);

    void addAttendanceFailed(String msg);


}
