package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpFragment;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;
import com.sdjzu.xg14.glmisattendanceandroid.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.sdjzu.xg14.glmisattendanceandroid.R.array.employees;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendanceFragmentLeft extends MvpFragment<GetEmployeeInfoPresenter> implements IGetEmployeeInfoView {
    private RecyclerView recyclerView;
    private List<Employee> mEmployees;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_attendance, container, false);
    }

    @Override
    public void setUpView(View view) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.list_divider));
    }

    @Override
    public void setUpData() {
        String[] employeeNames = getResources().getStringArray(employees);
        mEmployees = new ArrayList<>();
        for (int i = 0; i < employeeNames.length; i++) {
            Employee employee = new Employee();
            employee.setName(employeeNames[i]);
            mEmployees.add(employee);
        }
        recyclerView.setAdapter(new EmployeeAdapter(mEmployees));
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