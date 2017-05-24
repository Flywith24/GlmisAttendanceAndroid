package com.sdjzu.xg14.glmisattendanceandroid.attendance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.BasePresenter;
import com.sdjzu.xg14.glmisattendanceandroid.core.retrofit.ApiCallback;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

import java.util.List;

/**
 * Created on 24/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class GetEmployeeInfoPresenter extends BasePresenter<IGetEmployeeInfoView> {

    public GetEmployeeInfoPresenter(IGetEmployeeInfoView view) {
        attachView(view);
    }

    public void loadEmployeeData() {
        mvpView.showLoading();
        addSubscription(mApiStores.loadEmployeeData(),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        mvpView.loadEmployeeInfoSucceed(handleData(model));
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.loadEmployeeInfoFailed(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }

    private List<Employee> handleData(String model) {
        //解析数据
        Gson gson = new Gson();
        List<Employee> employees = gson.fromJson(model, new TypeToken<List<Employee>>() {
        }.getType());
        return employees;
    }
}
