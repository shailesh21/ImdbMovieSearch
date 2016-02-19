package com.data.movie.movieapp;

import com.activeandroid.app.Application;
import com.data.movie.movieapp.utils.Constants;
import com.data.movie.movieapp.utils.WebServiceInterface;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Shailesh on 18/02/16.
 */
public class BaseApplication extends Application {

    WebServiceInterface webServiceInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        webServiceInterface = initService();
    }

    private WebServiceInterface initService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(logging);
        okHttpClient.setConnectTimeout(Constants.CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(Constants.CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(WebServiceInterface.class);
    }

    public WebServiceInterface getService() {
        WebServiceInterface webService;
        if (webServiceInterface != null) {
            webService = webServiceInterface;
        } else {
            webService = initService();
        }
        return webService;
    }
}
