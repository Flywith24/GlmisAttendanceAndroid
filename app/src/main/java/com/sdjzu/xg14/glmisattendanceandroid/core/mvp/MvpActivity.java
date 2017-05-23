package com.sdjzu.xg14.glmisattendanceandroid.core.mvp;

import android.os.Bundle;

import com.sdjzu.xg14.glmisattendanceandroid.core.BaseActivity;

/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity{
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {

    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }
}
