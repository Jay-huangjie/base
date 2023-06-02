package com.jay.core.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by hj on 2020/11/23
 * Retrofit工厂类
 **/
public class RetrofitApiFactory {
    private volatile static RetrofitApiFactory instance;

    public static RetrofitApiFactory getInstance() {
        if (instance == null) {
            synchronized (RetrofitApiFactory.class) {
                if (instance == null) {
                    instance = new RetrofitApiFactory();
                }
            }
        }
        return instance;
    }

    private RetrofitApiFactory() {
        buildRetrofit();
    }

    private Retrofit mRetrofit;

    /**
     * Http连接超时
     */
    private final int CONNECT_TIMEOUT = 60;

    /**
     * Http 读取超时
     */
    private final int READ_TIMEOUT = 60;

    /**
     * Http 写入超时
     */
    private final int WRITE_TIMEOUT = 60;


    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        okhttpConfig(builder);
        return builder.build();
    }

    //上层额外配置重写此方法
    public void okhttpConfig(OkHttpClient.Builder builder) {

    }

    private void buildRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpManager.getHttpConfig().getBaseUrl())
                .client(buildOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//对转换后的数据进行再包装
                .build();
    }

    public <T> T createApi(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }
}
