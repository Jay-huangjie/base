package com.jay.core.mvvm.model.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.jay.core.mvvm.bean.Resource;
import com.jay.core.mvvm.model.livedata.action.NullableLiveDataAction;

/**
 * create by hj on 2022/4/15
 * 空回调，无返回值
 **/
public class NullableLiveData extends BaseResourceLiveData<Object> {

    /**
     * 新版本调用
     */
    public void callback(@NonNull LifecycleOwner owner, NullableLiveDataAction mAction) {
        observe(owner, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                mAction.onNext();
            } else if (resource.getStatus() == Resource.Status.LOADING) {
                mAction.doLoading();
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                mAction.doError(resource.getMsg());
            } else {
                Log.e("LiveData", "未知状态");
            }
        });
    }


    /*--------快速赋值-------------------------------------------*/

    public void setSuccess() {
        setValue(Resource.success(null));
    }

    // post系列方法为在子线程中调用
    public void postSuccess() {
        postValue(Resource.success(null));
    }
}
