package com.jay.core.mvvm.model.livedata.action;

/**
 * create by hj on 2022/4/15
 * 无返回值调用  依旧可以返回Error
 **/
public interface NullableLiveDataAction {
    void onNext();

    default void doError(String msg) {
    }

    default void doLoading() {
    }
}
