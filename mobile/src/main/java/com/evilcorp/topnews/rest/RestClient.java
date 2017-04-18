package com.evilcorp.topnews.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hristo.stoyanov on 3/31/2017.
 */

public class RestClient {
    private static final String REST_DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";
    private static final String BASE_URL = " https://newsapi.org/v1/";

    private static RestApi REST_INSTANCE;

    private RestClient(){
        Gson gson = new GsonBuilder()
                .setDateFormat(REST_DATE_FORMAT)
                //.registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        REST_INSTANCE = retrofit.create(RestApi.class);
    }

    public static RestApi getApi() {
        if (REST_INSTANCE == null) {
            synchronized (RestClient.class) {
                if (REST_INSTANCE == null) {
                    new RestClient();
                }
            }
        }
        return REST_INSTANCE;
    }
}
