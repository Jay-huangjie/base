package com.jay.core.mvvm.model.livedata;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.jay.core.mvvm.model.livedata.action.LiveDataAction;

/**
 * create by hj on 2020/11/23
 * LiveData第二层处理
 **/
public class BaseLiveData<T> extends MutableLiveData<T> {

    /**
     * 无回调调用,自动判空,为空不回调
     *
     * @param owner
     * @param mAction
     */
    public void doObserverIfNotNull(LifecycleOwner owner, LiveDataAction<T> mAction) {
        observe(owner, t -> {
            if (t != null) {
                mAction.onNext(t);
            } else {
                Log.e("LiveData", "Data is Null");
            }
        });
    }

    /**
     * 无回调调用
     */
    public void doObserver(LifecycleOwner owner, LiveDataAction<T> mAction) {
        observe(owner, mAction::onNext);
    }
}
