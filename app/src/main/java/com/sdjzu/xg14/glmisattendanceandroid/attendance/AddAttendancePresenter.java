package com.sdjzu.xg14.glmisattendanceandroid.attendance;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
import com.sdjzu.xg14.glmisattendanceandroid.core.retrofit.ApiCallback;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;

import java.util.Map;

/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AddAttendancePresenter extends BasePresenter<IAddAttendanceView> {
    public AddAttendancePresenter(IAddAttendanceView view) {
        attachView(view);
    }

    public void addAttendanceData(AttendanceSummary summary) {
        mvpView.showLoading();
        addSubscription(mApiStores.addAttendanceData(summary), new ApiCallback<String>() {
            @Override
            public void onSuccess(String str) {
                mvpView.addAttendanceSucceed(str);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
