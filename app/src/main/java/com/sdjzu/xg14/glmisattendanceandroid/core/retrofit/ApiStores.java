package com.sdjzu.xg14.glmisattendanceandroid.core.retrofit;

import com.sdjzu.xg14.glmisattendanceandroid.model.AttendanceSummary;
import com.sdjzu.xg14.glmisattendanceandroid.model.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public interface ApiStores {
    @POST("login.json")
    Observable<String> loadLoginData(@Body User user);

    @POST("getCandidates.json")
    Observable<String> loadEmployeeData();

    @POST("getAbsenceEmployeeIds.json")
    Observable<String> loadAbsenceEmployeeIds();

    @POST("addAttendanceSummary.json")
    Observable<String> addAttendanceData(@Body AttendanceSummary AttendanceSummary);

    @POST("updateAttendanceSummary.json")
    Observable<String> updateAttendanceData(@Body AttendanceSummary AttendanceSummary);

}

