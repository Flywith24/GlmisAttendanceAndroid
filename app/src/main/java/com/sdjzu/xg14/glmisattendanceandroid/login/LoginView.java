package com.sdjzu.xg14.glmisattendanceandroid.login;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.IBaseView;
import com.sdjzu.xg14.glmisattendanceandroid.model.User;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface LoginView extends IBaseView {
    void loginSuccess(User user);

    void loginFailed(String msg);
}

