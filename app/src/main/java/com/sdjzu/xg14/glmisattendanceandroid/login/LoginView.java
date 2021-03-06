package com.sdjzu.xg14.glmisattendanceandroid.login;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.IBaseView;
import com.sdjzu.xg14.glmisattendanceandroid.model.Employee;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface LoginView extends IBaseView {
    void loginSucceed(Employee employee);

    void loginFailed(String msg);
}

