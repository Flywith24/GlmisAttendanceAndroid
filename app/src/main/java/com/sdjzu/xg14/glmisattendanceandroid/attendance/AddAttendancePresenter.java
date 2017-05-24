package com.sdjzu.xg14.glmisattendanceandroid.attendance;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
import com.sdjzu.xg14.glmisattendanceandroid.core.retrofit.ApiCallback;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceRecord;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;

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

    public void addAttendanceData(AttendanceRecord record) {
        mvpView.showLoading();
        addSubscription(mApiStores.addAttendanceData(record), new ApiCallback() {
            @Override
            public void onSuccess(Object model) {
                mvpView.addAttendanceSucceed();
            }

            @Override
            public void onFailure(String msg) {
                mvpView.addAttendanceFailed(msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }


}
