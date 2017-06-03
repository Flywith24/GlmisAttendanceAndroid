package com.sdjzu.xg14.glmisattendanceandroid.core;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.idescout.sql.SqlScoutServer;
import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.constants.ConstantValues;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;


/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    protected Toolbar toolbar;
    protected TextView toolbar_title;
    protected ProgressDialog progressDialog;

    public static final int MODE_BACK = 0;
    public static final int MODE_DRAWER = 1;
    public static final int MODE_NONE = 2;
    public static final int MODE_HOME = 3;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SqlScoutServer.create(this, getPackageName());
        switch (AppStatusTracker.getInstance().getAppStatus()) {
            case ConstantValues.STATUS_FORCE_KILLED:
                protectApp();
                break;
            case ConstantValues.STATUS_KICK_OUT:
                kickOut();
                break;
            case ConstantValues.STATUS_LOGOUT:
            case ConstantValues.STATUS_OFFLINE:
            case ConstantValues.STATUS_ONLINE:
                setUpContentView();
                setUpView();
                setUpData(savedInstanceState);
                break;
        }
    }

    protected abstract void setUpContentView();

    protected abstract void setUpView();

    /**
     * 初始化数据
     * 被允许初始化才能进行初始化操作，防止应用被强杀而系统保存Activity栈信息导致空指针
     *
     * @param savedInstanceState
     */
    protected abstract void setUpData(Bundle savedInstanceState);


    /**
     * 当应用被强杀时调用此方法，返回到主页，进而重新走应用流程
     */
    protected void protectApp() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_RESTART_APP);
        startActivity(intent);
    }

    protected void kickOut() {
//        TODO show dialog to confirm
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_KICK_OUT);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, -1, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId) {
        setContentView(layoutResID, titleResId, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId, int mode) {
        setContentView(layoutResID, titleResId, -1, mode);
    }

    public void setContentView(int layoutResID, int titleResId, int menuId, int mode) {
        super.setContentView(layoutResID);
        setUpToolbar(titleResId, menuId, mode);
    }

    protected void setUpToolbar(int titleResId, int menuId, int mode) {
        if (mode != MODE_NONE) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("");//这条语句要在setSupportActionBar之前
            setSupportActionBar(toolbar);//将actionBar设置成toolbar的样式
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            //带返回键的
            if (mode == MODE_BACK) {
                toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
            } else if (mode == MODE_DRAWER) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_toolbar_drawer);
            }

            setUpTitle(titleResId);
            setUpMenu(menuId);
        }
    }

    protected void setUpMenu(int menuId) {
        if (toolbar != null) {
            toolbar.getMenu().clear();
            if (menuId > 0) {
                toolbar.inflateMenu(menuId);
                toolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && toolbar_title != null) {
            toolbar_title.setText(titleResId);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    protected void onStart() {
        if (AppStatusTracker.getInstance().checkIfShowGesture()) {
            L.d(TAG, "need to show gesture");
        }
        super.onStart();
    }

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中");
//        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }
}
