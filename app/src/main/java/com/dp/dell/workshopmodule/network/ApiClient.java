package com.dp.dell.workshopmodule.network;


import com.dp.dell.workshopmodule.utils.ConfigurationFile;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 04/04/2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS).build();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigurationFile.UrlConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
