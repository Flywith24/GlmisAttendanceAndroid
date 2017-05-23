package com.sdjzu.xg14.glmisattendanceandroid.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdjzu.xg14.glmisattendanceandroid.HomeActivity;
import com.sdjzu.xg14.glmisattendanceandroid.R;
import com.sdjzu.xg14.glmisattendanceandroid.constants.ConstantValues;
import com.sdjzu.xg14.glmisattendanceandroid.core.AppStatusTracker;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.MvpActivity;
import com.sdjzu.xg14.glmisattendanceandroid.model.User;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private String uername;
    private String password;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setUpView() {
        mUsername = (EditText) findViewById(R.id.username_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        mLogin = (Button) findViewById(R.id.login_btn);
        mLogin.setOnClickListener(this);
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
        uername = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(uername) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
//            mvpPresenter.loadLoginData(new User(uername, password));
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_ONLINE);
        }
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

    @Override
    public void loginSuccess(User user) {
        jumpToHomePage();
    }

    @Override
    public void loginFailed(String msg) {
        L.d("登录失败：", msg);
    }

    /**
     * 跳转到主页
     */
    private void jumpToHomePage() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}