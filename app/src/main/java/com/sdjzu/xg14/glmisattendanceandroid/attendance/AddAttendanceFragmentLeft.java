package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class AddAttendanceFragmentLeft extends BaseFragment {
    private List<Employee> mEmployeesLeft = new ArrayList<>();
    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_attendance, container, false);
    }

    @Override
    protected void refresh() {
        mEmployeesLeft.clear();
        mEmployeesLeft.addAll(MyApplication.getInstances().getDaoSession().getEmployeeDao()
                .queryBuilder().where(EmployeeDao.Properties.IsAttendant.eq(false)).list());
        adapter.addList(mEmployeesLeft);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUpView(View view) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.list_divider));
        adapter = new EmployeeAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUpData() {
        L.d("at left");
        mEmployeesLeft.addAll(MyApplication.getInstances().getDaoSession().getEmployeeDao()
                .queryBuilder().where(EmployeeDao.Properties.IsAttendant.eq(false)).list());
        adapter.addList(mEmployeesLeft);
        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Employee employee = mEmployeesLeft.get(position);
                employee.setIsAttendant(true);
                MyApplication.getInstances().getDaoSession().getEmployeeDao().update(employee);
                mEmployeesLeft.remove(position);
                adapter.removeData(position);
            }
        });
    }
}
