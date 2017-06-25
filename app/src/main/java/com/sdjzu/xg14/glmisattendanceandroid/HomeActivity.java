package com.sdjzu.xg14.glmisattendanceandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.sdjzu.xg14.glmisattendanceandroid.addAttendance.AddAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.core.BaseActivity;
import com.sdjzu.xg14.glmisattendanceandroid.login.LoginActivity;
import com.sdjzu.xg14.glmisattendanceandroid.updateAttendance.UpdateAttendanceActivity;
import com.sdjzu.xg14.glmisattendanceandroid.utils.ActivityCollector;
import com.sdjzu.xg14.glmisattendanceandroid.utils.T;

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
                int id = item.getItemId();
                if (id == R.id.nav_info) {
                    T.showToast(HomeActivity.this, "我的信息");
                } else if (id == R.id.nav_logout) {
                    confirmDialog();
                } else if (id == R.id.nav_about) {
                    T.showToast(HomeActivity.this, "关于软件");
                }
                mDrawerLayout.closeDrawers();
                return false;
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

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("确定退出登录？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                });
        builder.show();
    }

    /**
     * 显示输入考勤名称的dialog
     */
    private void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.widget_my_dialog, (ViewGroup) findViewById(R.id.dialog));
        final EditText et_name = (EditText) view.findViewById(R.id.et);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("请输入考勤名称")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null)
                .setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("".equals(et_name.getText().toString())) {
                        T.showToast(HomeActivity.this, "考勤名称不能为空");
                    } else {
                        dialog.dismiss();
                        Intent intent = new Intent(HomeActivity.this, AddAttendanceActivity.class);
                        intent.putExtra("attendance_name", et_name.getText().toString());
                        HomeActivity.this.startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_attendance:
                showDialog();
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
