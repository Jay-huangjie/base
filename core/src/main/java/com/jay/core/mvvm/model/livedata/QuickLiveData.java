package com.jay.core.mvvm.model.livedata;

import androidx.lifecycle.LifecycleOwner;

import com.jay.core.mvvm.model.livedata.action.LiveDataSimpleAction;


/**
 * create by hj on 2022/2/21
 * BaseLiveData 快速实现类
 **/
public class QuickLiveData<T> {
    private BaseResourceLiveData<T> mLiveData = new BaseResourceLiveData<>();

    public BaseResourceLiveData<T> getLiveData() {
        return mLiveData;
    }

    public void setSuccess(T data){
        mLiveData.setSuccess(data);
    }

    public void setLiveData(BaseResourceLiveData<T> mLiveData) {
        this.mLiveData = mLiveData;
    }

    public QuickLiveData(LifecycleOwner owner, LiveDataSimpleAction<T> mAction) {
        mLiveData.callback(owner, mAction);
    }
}
