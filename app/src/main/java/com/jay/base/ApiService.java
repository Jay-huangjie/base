package com.jay.base;

import com.jay.base.bean.Result;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * created by hj on 2023/5/11.
 */
public interface ApiService {

    /**
     * 获取回复和反馈
     *
     * @return
     */
    @GET("api/home/appconfig/appInfo")
    Observable<Result<String>> getAppInfo(@QueryMap Map<String, String> dynamic);

    /**
     * 获取回复和反馈
     *
     * @return
     */
    @GET
    Observable<Object> getTime(@Url String url);
}
