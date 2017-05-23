package com.sdjzu.xg14.glmisattendanceandroid.core.mvp;

import android.os.Bundle;
import android.view.View;

import com.sdjzu.xg14.glmisattendanceandroid.core.BaseFragment;

/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */


public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
        initData();
    }

    protected abstract void initData();

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
