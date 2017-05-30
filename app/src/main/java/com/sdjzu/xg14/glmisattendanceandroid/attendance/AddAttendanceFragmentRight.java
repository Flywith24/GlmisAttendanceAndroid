package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpFragment;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;

import java.util.List;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendanceFragmentRight extends MvpFragment<GetEmployeeInfoPresenter> implements IGetEmployeeInfoView ,AddAttendanceActivity.RightListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_attendance, container, false);
    }

    @Override
    public void setUpView(View view) {

        ((AddAttendanceActivity) getActivity()).setRightListener(this);

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


    @Override
    public void onPageSelected(int position) {
        L.d("right");
    }
}
