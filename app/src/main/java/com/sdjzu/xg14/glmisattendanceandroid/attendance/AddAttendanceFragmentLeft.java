package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.view.View;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpFragment;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendanceFragmentLeft extends MvpFragment<GetEmployeeInfoPresenter> implements IGetEmployeeInfoView {
    @Override
    public void setUpView(View view) {

    }

    @Override
    public void setUpData() {

    }

    @Override
    public void loadEmployeeInfoSucceed(List<Employee> employees) {

    }

    @Override
    public void loadEmployeeInfoFailed(String msg) {

    }

    @Override
    protected GetEmployeeInfoPresenter createPresenter() {
        return null;
    }


}
