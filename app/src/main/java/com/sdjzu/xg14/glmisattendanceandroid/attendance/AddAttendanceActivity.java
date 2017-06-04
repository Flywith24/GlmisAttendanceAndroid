package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gigamole.library.navigationtabstrip.NavigationTabStrip;
import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.ArrayList;
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
    private AddAttendanceFragmentLeft mFragmentLeft;
    private AddAttendanceFragmentRight mFragmentRight;
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
    }

    @Override
    protected AddAttendancePresenter createPresenter() {
        return new AddAttendancePresenter(this);
    }


    @Override
    public void addAttendanceSucceed(String str) {
        if (!"".equals(str)) {
            mSummary.setId(Long.parseLong(str));
            mSummary.setAttendanceManager("yyz");
            MyApplication.getInstances().getDaoSession().getAttendanceSummaryDao().insert(mSummary);
            startActivity(new Intent(AddAttendanceActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "提交失败，请重新提交", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addAttendanceFailed(String msg) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_commit:
                DaoSession daoSession = MyApplication.getInstances().getDaoSession();
                daoSession.getAttendanceSummaryDao().deleteAll();
                mSummary = new AttendanceSummary();
                mSummary.setAttendanceName("第二次");
                mSummary.setAttendanceTime("2017-06-03");
                List<Employee> employees = daoSession.getEmployeeDao().queryBuilder().where(EmployeeDao.Properties.IsAttendant.eq(false)).list();
                List<Long> employeeIds = new ArrayList<>();
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
                        mFragmentLeft = new AddAttendanceFragmentLeft();
                    }
                    return mFragmentLeft;

                case 1:
                    if (null == mFragmentRight) {
                        mFragmentRight = new AddAttendanceFragmentRight();
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
