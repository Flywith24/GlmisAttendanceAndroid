package com.sdjzu.xg14.glmisattendanceandroid.updateAttendance;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
import com.sdjzu.xg14.glmisattendanceandroid.core.retrofit.ApiCallback;
import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;

/**
 * Created on 06/06/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class UpdateAttendancePresenter extends BasePresenter<IUpdateAttendanceView> {
    public UpdateAttendancePresenter(IUpdateAttendanceView view) {
        attachView(view);
    }

    public void updateAttendanceData(AttendanceSummary summary) {
//        String a = summary.toString();
        mvpView.showLoading();
        addSubscription(mApiStores.updateAttendanceData(summary), new ApiCallback<String>() {
            @Override
            public void onSuccess(String str) {
                mvpView.updateAttendanceSucceed(str);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.updateAttendanceFailed(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
