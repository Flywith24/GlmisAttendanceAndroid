package com.sdjzu.xg14.glmisattendanceandroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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
    private Button mLogin;
    private String username;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
     * @param userStr 从服务器返回的json数据
     */
    @Override
    public void loginSucceed(String userStr) {
        if ("".equals(userStr)) {
            T.showToast(this, R.string.wrong_name_password);
        } else {
            saveLoginInfo();
            jumpToHomePage();
        }

    }

    @Override
    public void loginFailed(String msg) {
        L.d("yyz登录失败：", msg);
    }

    /**
     * 跳转到主页
     */
    private void jumpToHomePage() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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
        editor.putString(String.valueOf(R.string.username), username);
        editor.putString(String.valueOf(R.string.password), password);
        editor.apply();
    }

    /**
     * 从SharePreferences中获取登录信息，有则跳转直接跳转到HomeActivity
     */

    private void getLoginInfoFromSP() {
        SharedPreferences pref = getSharedPreferences(String.valueOf(R.string.login_info), MODE_PRIVATE);
        String username = pref.getString(String.valueOf(R.string.username), null);
        String password = pref.getString(String.valueOf(R.string.password), null);
        if (username != null && password != null) {
            jumpToHomePage();
        }
    }
}