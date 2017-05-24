package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.IBaseView;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface IGetEmployeeInfoView extends IBaseView {
    void loadEmployeeInfoSucceed(List<Employee> employees);

    void loadEmployeeInfoFailed(String msg);
}
