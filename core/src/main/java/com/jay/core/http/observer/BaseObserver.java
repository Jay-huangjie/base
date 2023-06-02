package com.jay.core.http.observer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.jay.core.BuildConfig;
import com.jay.core.http.HttpCodeConstant;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import retrofit2.HttpException;

/**
 * 接收者基类
 * @param <T> 服务器返回的最外层实体类，参考{@link com.jay.core.mvvm.bean.Result}
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onComplete() {
    }

    protected abstract void onResponseError(int code, String message);

    @Override
    public void onError(@NonNull Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e("OkHttp", "Error:" + e.getMessage());
        }
        String msg = null;
        int code = -1;
        if (e instanceof HttpException) {             //HTTP错误
            code = ((HttpException) e).code();
            if (code == HttpCodeConstant.SERVER_UNAVAILABLE_CODE
                    || code == HttpCodeConstant.GATEWATTIMEDOUT_CODE) {
                msg = HttpCodeConstant.BAD_NET_WORK;
            } else if (code == HttpCodeConstant.REQUEST_TIMEOUT_CODE) { //请求超时
                //处理408错误
                msg = HttpCodeConstant.REQUEST_TIMEOUT;
            } else if (BuildConfig.DEBUG) {
                switch (code) {
                    //未授权
                    case HttpCodeConstant.UNAUTHORIZED_CODE:
                        msg = HttpCodeConstant.UNAUTHORIZED;
                        break;
                    //禁止访问
                    case HttpCodeConstant.FORBIDDEN_CODE:
                        //处理403错误
                        msg = HttpCodeConstant.FORBIDDEN;
                        break;
                    //无法找到资源
                    case HttpCodeConstant.NOT_FOUND_CODE:
                        //处理404错误
                        msg = HttpCodeConstant.NOT_FOUND;
                        break;
                    //请求方法不允许
                    case HttpCodeConstant.METHOD_NOT_ALLOWED_CODE:
                        //处理405错误
                        msg = HttpCodeConstant.METHOD_NOT_ALLOWED;
                        break;
                    //服务器内部错误
                    case HttpCodeConstant.BAD_GATEWAY_CODE:
                    case HttpCodeConstant.INTERNAL_SERVER_ERROR_CODE:
                        //处理500错误
                        msg = HttpCodeConstant.INTERNAL_SERVER_ERROR;
                        break;
                    //请求出错
                    case HttpCodeConstant.BAD_REQUEST_CODE:
                        //处理400错误
                        msg = HttpCodeConstant.BAD_REQUEST;
                        break;
                    default:
                        msg = HttpCodeConstant.UNKNOW_ERROR;
                }
            }
        } else if (e instanceof JSONException
                || e instanceof ParseException
                || e instanceof JsonParseException
        ) {
            if (BuildConfig.DEBUG) {
                msg = HttpCodeConstant.PARSE_ERROR;
            } else {
                msg = "";
            }
        } else if (e instanceof IOException) {
            if (e instanceof SocketTimeoutException || e instanceof ConnectException
                    || e instanceof SSLHandshakeException) {
                code = HttpCodeConstant.REQUEST_TIMEOUT_CODE;
                msg = HttpCodeConstant.REQUEST_TIMEOUT;
            } else {
                if (BuildConfig.DEBUG) {
                    msg = HttpCodeConstant.PARSE_ERROR;
                } else {
                    msg = "";
                }
            }
        } else {
            msg = HttpCodeConstant.UNKNOW_ERROR;
        }
        onResponseError(code, msg);
    }
}
