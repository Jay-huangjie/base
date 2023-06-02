package com.jay.core.http.listener;

public interface HttpClientListener<T> {
    void onSuccess(T t);

    void onError(int code, String message);
}
