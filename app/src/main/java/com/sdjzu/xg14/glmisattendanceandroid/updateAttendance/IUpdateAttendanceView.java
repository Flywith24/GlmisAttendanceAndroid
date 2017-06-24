package com.sdjzu.xg14.glmisattendanceandroid.updateAttendance;

import com.sdjzu.xg14.glmisattendanceandroid.core.mvp.IBaseView;

/**
 * Created on 06/06/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface IUpdateAttendanceView extends IBaseView{
    void updateAttendanceSucceed(String str);

    void updateAttendanceFailed(String msg);
}
