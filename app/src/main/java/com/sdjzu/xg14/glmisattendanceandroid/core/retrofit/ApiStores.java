package com.sdjzu.xg14.glmisattendanceandroid.core.retrofit;

import com.sdjzu.xg14.glmisattendanceandroid.model.Admin;

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
    Observable<String> loadLoginData(@Body Admin admin);

}
