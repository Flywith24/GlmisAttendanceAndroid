package com.sdjzu.xg14.glmisattendanceandroid.updateAttendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.gigamole.library.navigationtabstrip.NavigationTabStrip;
import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.addAttendance.AddAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.addAttendance.AttendanceFragmentLeft;
import com.sdjzu.xg14.glmisattendanceandroid.addAttendance.AttendanceFragmentRight;
import com.sdjzu.xg14.glmisattendanceandroid.core.MyApplication;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.AttendanceSummaryDao;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.DaoSession;
import com.sdjzu.xg14.glmisattendanceandroid.greendao.EmployeeDao;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.utils.T;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 06/06/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class UpdateAttendanceActivity extends MvpActivity<UpdateAttendancePresenter> implements IUpdateAttendanceView {
    private ViewPager mViewPager;
    private NavigationTabStrip mTopNavigationTabStrip;
    private AttendanceFragmentLeft mFragmentLeft;
    private AttendanceFragmentRight mFragmentRight;
    private AttendanceSummary mSummary = null;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_attendance, R.string.app_name, MODE_BACK);
    }

    @Override
    protected UpdateAttendancePresenter createPresenter() {
        return new UpdateAttendancePresenter(this);
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tbar_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_update:
                DaoSession daoSession = MyApplication.getInstances().getDaoSession();
                //根据id获取summary

                List<AttendanceSummary> summaries = daoSession.getAttendanceSummaryDao().loadAll();
                for (AttendanceSummary attendanceSummary : summaries) {
                    mSummary = attendanceSummary;
                }
                List<Employee> employees = daoSession.getEmployeeDao().queryBuilder()
                        .where(EmployeeDao.Properties.IsAttendant.eq(false)).list();
                List<Long> employeeIds = new ArrayList<>();
                for (Employee employee : employees) {
                    employeeIds.add(employee.getId());
                }
                mSummary.setEmployeeIds(employeeIds);
                mvpPresenter.updateAttendanceData(mSummary);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateAttendanceSucceed(String str) {
        if (!"".equals(str)) {
            long summaryId = Long.parseLong(str);
            mSummary.setId(summaryId);//将从服务器返回的summaryId转为Long类型并mSummary的id属性赋值


            AttendanceSummaryDao summaryDao = MyApplication.getInstances().getDaoSession()
                    .getAttendanceSummaryDao();
            summaryDao.deleteAll();
            summaryDao.insert(mSummary);//将mSummary存入本地数据库，此时已完整（有id）。
            T.showToast(this, "更新成功");
            startActivity(new Intent(UpdateAttendanceActivity.this, HomeActivity.class));
        } else {
            T.showToast(this, "更新失败，请重新提交");
        }
    }

    @Override
    public void updateAttendanceFailed(String msg) {
        T.showToast(this, msg);
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
