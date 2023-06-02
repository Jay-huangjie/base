package com.jay.core.http.impl;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.jay.core.http.HttpCodeConstant;
import com.jay.core.http.RxSchedulers;
import com.jay.core.http.conver.BaseClient;
import com.jay.core.http.conver.IResult;
import com.jay.core.http.listener.HttpClientListener;
import com.jay.core.http.observer.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 上层实现参考
 */
public class HttpClient extends BaseClient {

    private static final int SUCCESS = 1;

    @Override
    public <D, T extends IResult<D>> void client(Observable<T> observable, HttpClientListener<D> listener) {
        if (NetworkUtils.isConnected()) {
            observable.compose(RxSchedulers.compose())
                    .subscribe(new BaseObserver<T>() {
                        @Override
                        protected void onResponseError(int code, String message) {
                            listener.onError(code, message);
                        }

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDispose(d);
                        }

                        @Override
                        public void onNext(@NonNull T result) {
                            if (result.getCode() == SUCCESS) {
                                //接口回调成功,返回数据
                                listener.onSuccess(result.getData());
                            } else {
                                listener.onError(result.getCode(), result.getMsg());
                            }
                        }
                    });
        } else {
            ThreadUtils.runOnUiThread(() -> listener.onError(HttpCodeConstant.SERVER_UNAVAILABLE_CODE, HttpCodeConstant.BAD_NET_WORK));
        }
    }

    @Override
    public <T> void clientTransformResult(Observable<T> observable, HttpClientListener<T> listener) {
        if (NetworkUtils.isConnected()) {
            observable.compose(RxSchedulers.compose())
                    .subscribe(new BaseObserver<T>() {
                        @Override
                        protected void onResponseError(int code, String message) {
                            listener.onError(code, message);
                        }

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDispose(d);
                        }

                        @Override
                        public void onNext(@NonNull T result) {
                            listener.onSuccess(result);
                        }
                    });
        } else {
            ThreadUtils.runOnUiThread(() -> listener.onError(HttpCodeConstant.SERVER_UNAVAILABLE_CODE, HttpCodeConstant.BAD_NET_WORK));
        }
    }

    @Override
    public void clientTransformString(Observable<Object> observable, HttpClientListener<String> listener) {
        if (NetworkUtils.isConnected()) {
            observable.compose(RxSchedulers.compose())
                    .subscribe(new BaseObserver<Object>() {
                        @Override
                        protected void onResponseError(int code, String message) {
                            listener.onError(code, message);
                        }

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDispose(d);
                        }

                        @Override
                        public void onNext(@NonNull Object result) {
                            listener.onSuccess(result.toString());
                        }
                    });
        } else {
            ThreadUtils.runOnUiThread(() -> listener.onError(HttpCodeConstant.SERVER_UNAVAILABLE_CODE, HttpCodeConstant.BAD_NET_WORK));
        }
    }

    @Override
    public <T> T createApi() {
        return null;
    }
}
