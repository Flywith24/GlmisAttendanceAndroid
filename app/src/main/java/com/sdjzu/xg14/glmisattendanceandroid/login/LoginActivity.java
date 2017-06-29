package com.sdjzu.xg14.glmisattendanceandroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.constants.ConstantValues;
import com.sdjzu.xg14.glmisattendanceandroid.core.AppStatusTracker;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;
import com.sdjzu.xg14.glmisattendanceandroid.model.User;
import com.sdjzu.xg14.glmisattendanceandroid.utils.KeyBoardHelper;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;
import com.sdjzu.xg14.glmisattendanceandroid.utils.T;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private LinearLayout mLayoutContent;
    private LinearLayout mLayoutBottom;
    private int bottomHeight;
    private String username;
    private String password;
    private KeyBoardHelper mKeyBoardHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoginInfoFromSP();
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login, -1, MODE_NONE);
    }

    @Override
    protected void setUpView() {
        mUsername = (EditText) findViewById(R.id.username_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        Button login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        mLayoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        mLayoutContent = (LinearLayout) findViewById(R.id.layout_content);
        softKeyboardEvent();
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        //点击登陆按钮，隐藏软键盘
        hideSoftKeyboard(view);
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            T.showToast(this, "用户名或密码不能为空");
        } else {
            L.d("yyz", username);
            L.d("yyz", password);
            mvpPresenter.loadLoginData(new User(username, password));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mKeyBoardHelper.onDestory();
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 从服务器成功返回数据，无论用户名或密码是否正确只要成功通信都会调用此方法，密码是否正确的逻辑写在这里
     *
     * @param employee 从服务器返回的json数据
     */
    @Override
    public void loginSucceed(Employee employee) {
        if ("".equals(employee)) {
            T.showToast(this, R.string.wrong_name_password);
        } else {
            saveLoginInfo();
            jumpToHomePage(employee);
        }

    }

    @Override
    public void loginFailed(String msg) {
        L.d("yyz登录失败：", msg);
    }

    /**
     * 跳转到主页
     */
    private void jumpToHomePage(Employee employee) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("employeeInfo", employee.getName());
        startActivity(intent);
        finish();
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_ONLINE);
    }

    /**
     * 软键盘出现时不遮挡登陆按钮
     */
    private void softKeyboardEvent() {
        mKeyBoardHelper = new KeyBoardHelper(this);
        mKeyBoardHelper.onCreate();
        mKeyBoardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
        mLayoutBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = mLayoutBottom.getHeight();
            }
        });
    }

    //软件盘状态改变的监听
    private KeyBoardHelper.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener =
            new KeyBoardHelper.OnKeyBoardStatusChangeListener() {

                @Override
                public void OnKeyBoardPop(int keyBoardheight) {

                    final int height = keyBoardheight;
                    if (bottomHeight > height) {
                        mLayoutBottom.setVisibility(View.GONE);
                    } else {
                        int offset = bottomHeight - height;
                        final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mLayoutContent
                                .getLayoutParams();
                        lp.topMargin = offset;
                        mLayoutContent.setLayoutParams(lp);
                    }

                }

                @Override
                public void OnKeyBoardClose(int oldKeyBoardheight) {
                    if (View.VISIBLE != mLayoutBottom.getVisibility()) {
                        mLayoutBottom.setVisibility(View.VISIBLE);
                    }
                    final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mLayoutContent
                            .getLayoutParams();
                    if (lp.topMargin != 0) {
                        lp.topMargin = 0;
                        mLayoutContent.setLayoutParams(lp);
                    }

                }
            };

    /**
     * 跳转到主页
     */
    private void jumpToHomePage() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_ONLINE);
    }

    /**
     * 保存登录信息到SharePreferences中
     */
    private void saveLoginInfo() {
        //将用户名密码存到Preferences,登录一次后就不需登录了
        SharedPreferences.Editor editor = getSharedPreferences
                (String.valueOf(R.string.login_info), MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    /**
     * 从SharePreferences中获取登录信息，有则跳转直接跳转到HomeActivity
     */

    private void getLoginInfoFromSP() {
        SharedPreferences pref = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE);
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);
        if (username != null && password != null) {
            jumpToHomePage();
        }
    }
}