package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.BaseFragment;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;
import com.sdjzu.xg14.glmisattendanceandroid.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendanceFragmentRight extends BaseFragment {
    private List<Employee> mEmployeesRight = new ArrayList<>();

    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLazyLoad();
    }

    @Override
    public void setUpView(View view) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.list_divider));
        adapter = new EmployeeAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpData() {
        L.d("at right");
        mEmployeesRight.addAll(MyApplication.getInstances().getDaoSession().getEmployeeDao()
                .queryBuilder().where(EmployeeDao.Properties.IsAttendant.eq(true)).list());
        adapter.addList(mEmployeesRight);
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Employee employee = mEmployeesRight.get(position);
                employee.setIsAttendant(false);
                MyApplication.getInstances().getDaoSession().getEmployeeDao().update(employee);
                mEmployeesRight.remove(position);
                adapter.removeData(position);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_attendance_right, container, false);
    }

    @Override
    protected void refresh() {
        mEmployeesRight.clear();
        mEmployeesRight.addAll(MyApplication.getInstances().getDaoSession().getEmployeeDao()
                .queryBuilder().where(EmployeeDao.Properties.IsAttendant.eq(true)).list());
        adapter.addList(mEmployeesRight);
        adapter.notifyDataSetChanged();
    }
}