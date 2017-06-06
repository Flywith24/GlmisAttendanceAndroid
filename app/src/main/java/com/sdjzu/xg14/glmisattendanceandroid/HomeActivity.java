package com.sdjzu.xg14.glmisattendanceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sdjzu.xg14.glmisattendanceandroid.attendance.AddAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.attendance.GetEmployeeInfoPresenter;
import com.sdjzu.xg14.glmisattendanceandroid.attendance.IGetEmployeeInfoView;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.widgets.MyDialog;

import java.util.List;


public class HomeActivity extends MvpActivity<GetEmployeeInfoPresenter> implements View.OnClickListener, IGetEmployeeInfoView {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected GetEmployeeInfoPresenter createPresenter() {
        return new GetEmployeeInfoPresenter(this);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_home, R.string.app_name, MODE_DRAWER);
    }

    @Override
    protected void setUpView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        Button addAttendance = (Button) findViewById(R.id.add_attendance);
        addAttendance.setOnClickListener(this);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        mvpPresenter.loadEmployeeData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_attendance:
                MyDialog dialog = new MyDialog(this, "新建考勤");
                dialog.showDialog();

                break;
            case R.id.update_attendance:
                //TODO

                break;
        }
    }

    @Override
    public void loadEmployeeInfoSucceed(List<Employee> employees) {
        //每次加载先清空本地数据
        MyApplication.getInstances().getDaoSession().getEmployeeDao().deleteAll();
        for (Employee employee : employees) {
            employee.setIsAttendant(false);
            MyApplication.getInstances().getDaoSession().getEmployeeDao().insert(employee);
        }
    }

    @Override
    public void loadEmployeeInfoFailed(String msg) {

    }
}
