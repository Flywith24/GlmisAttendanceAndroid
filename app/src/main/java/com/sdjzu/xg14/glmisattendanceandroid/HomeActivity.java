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

import com.sdjzu.xg14.glmisattendanceandroid.core.BaseActivity;
import com.sdjzu.xg14.glmisattendanceandroid.updateAttendance.UpdateAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.widgets.MyDialog;




public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


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
        Button updateAttendance = (Button) findViewById(R.id.update_attendance);
        updateAttendance.setOnClickListener(this);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
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
                Intent intent = new Intent(HomeActivity.this, UpdateAttendanceActivity.class);
                //获取从AttendanceActivity传来的summaryId并存入intent中，以便传递给UpdateAttendanceActivity
                intent.putExtra("summaryId", getIntent().getLongExtra("summaryId", -1));
                startActivity(intent);
                break;
        }
    }

}
