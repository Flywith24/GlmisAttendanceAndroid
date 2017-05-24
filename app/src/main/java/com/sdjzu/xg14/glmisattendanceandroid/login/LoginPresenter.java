package com.sdjzu.xg14.glmisattendanceandroid.login;

import com.google.gson.Gson;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
import com.sdjzu.xg14.glmisattendanceandroid.core.retrofit.ApiCallback;
import com.sdjzu.xg14.glmisattendanceandroid.model.User;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView view) {
        attachView(view);
    }

    public void loadLoginData(final User user) {
        mvpView.showLoading();
        addSubscription(mApiStores.loadLoginData(user),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String userStr) {
                        mvpView.loginSucceed(user);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.loginFailed(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }

}
