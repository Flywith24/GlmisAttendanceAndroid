package com.sdjzu.xg14.glmisattendanceandroid.core.retrofit;

import com.sdjzu.xg14.glmisattendanceandroid.constants.ConstantValues;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created on 23/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AppClient {
    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //设置超时时间
            builder.connectTimeout(ConstantValues.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(ConstantValues.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(ConstantValues.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ConstantValues.API_SERVER_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
