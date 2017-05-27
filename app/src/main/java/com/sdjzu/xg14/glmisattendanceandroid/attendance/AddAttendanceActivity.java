package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.library.navigationtabstrip.NavigationTabStrip;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;


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

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_add_attendance, R.string.add_attendance, MODE_BACK);
    }

    @Override
    protected void setUpView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTopNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_center);
//        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = new View(getBaseContext());
                container.addView(view);
                return view;
            }
        });

        mTopNavigationTabStrip.setViewPager(mViewPager, 1);

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
    public void addAttendanceSucceed() {

    }

    @Override
    public void addAttendanceFailed(String msg) {

    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
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

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(final View container, final int position, final Object object) {
            ((ViewPager) container).removeView((View) object);
        }


    }
}
