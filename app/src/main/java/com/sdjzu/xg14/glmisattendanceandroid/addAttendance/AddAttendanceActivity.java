package com.sdjzu.xg14.glmisattendanceandroid.addAttendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gigamole.library.navigationtabstrip.NavigationTabStrip;
import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.utils.T;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendanceActivity extends MvpActivity<AddAttendancePresenter> implements IAddAttendanceView {
    private ViewPager mViewPager;
    private NavigationTabStrip mTopNavigationTabStrip;
    private AttendanceFragmentLeft mFragmentLeft;
    private AttendanceFragmentRight mFragmentRight;
    private AttendanceSummary mSummary;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_attendance, R.string.app_name, MODE_BACK);
    }

    @Override
    protected void setUpView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTopNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_center);
        mViewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        mTopNavigationTabStrip.setViewPager(mViewPager);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        super.setUpData(savedInstanceState);
        mvpPresenter.loadEmployeeData();
    }

    @Override
    protected AddAttendancePresenter createPresenter() {
        return new AddAttendancePresenter(this);
    }


    @Override
    public void addAttendanceSucceed(String str) {
        if (!"".equals(str)) {
            long summaryId = Long.parseLong(str);
            mSummary.setId(summaryId);//将从服务器返回的summaryId转为Long类型并mSummary的id属性赋值

            MyApplication.getInstances().getDaoSession()
                    .getAttendanceSummaryDao().insert(mSummary);//将mSummary存入本地数据库，此时已完整（有id）。
            T.showToast(this, "提交成功");
            Intent intent = new Intent(AddAttendanceActivity.this, HomeActivity.class);//返回到HomeActivity
            intent.putExtra("summaryId", summaryId);//将返回的summaryId保存到intent中，updateActivity需要使用id
            startActivity(intent);
            finish();
        } else {
            T.showToast(this, "提交失败，请重新提交");
        }
    }

    @Override
    public void addAttendanceFailed(String msg) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tbar_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_commit:        //点击"提交"时执行
                DaoSession daoSession = MyApplication.getInstances().getDaoSession();
                daoSession.getAttendanceSummaryDao().deleteAll();

                mSummary = new AttendanceSummary();
                mSummary.setAttendanceName(
                        getIntent().getStringExtra("attendance_name"));//设置考勤名称
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                mSummary.setAttendanceTime(formatter.format(
                        new Date(System.currentTimeMillis())));//设置考勤时间为当前日期

                //从本地数据库获取未出勤的employee保存至employees中，
                List<Employee> employees = daoSession.getEmployeeDao().queryBuilder()
                        .where(EmployeeDao.Properties.IsAttendant.eq(false)).list();
                List<Long> employeeIds = new ArrayList<>();
                //只取出employees的id，服务器只需要未出勤employee的id
                for (Employee employee : employees) {
                    employeeIds.add(employee.getId());
                }
                mSummary.setEmployeeIds(employeeIds);
                mvpPresenter.addAttendanceData(mSummary);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class myPagerAdapter extends FragmentPagerAdapter {

        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    if (null == mFragmentLeft) {
                        mFragmentLeft = new AttendanceFragmentLeft();
                    }
                    return mFragmentLeft;

                case 1:
                    if (null == mFragmentRight) {
                        mFragmentRight = new AttendanceFragmentRight();
                    }
                    return mFragmentRight;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
