package com.jay.core.mvvm.model.livedata.action;

/**
 * create by hj on 2022/4/15
 **/
public interface LiveDataSimpleAction<T> extends LiveDataAction<T> {
    default void doError(String msg) {
    }

    default void doLoading(T data) {
    }
}
