package com.jay.core.mvvm.model.livedata.action;

/**
 * create by hj on 2020/11/23
 **/
public interface LiveDataAction<T> {
    void onNext(T data);
}
