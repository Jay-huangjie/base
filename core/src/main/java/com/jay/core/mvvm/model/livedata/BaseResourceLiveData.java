package com.jay.core.mvvm.model.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.jay.core.mvvm.bean.Resource;
import com.jay.core.mvvm.model.livedata.action.LiveDataSimpleAction;


/**
 * create by hj on 2022/4/15
 * 此系列方法的基类是{@link Resource}
 **/
public class BaseResourceLiveData<T> extends BaseLiveData<Resource<T>> {

    /**
     * 新版本调用
     */
    public void callback(@NonNull LifecycleOwner owner, LiveDataSimpleAction<T> mAction) {
        observe(owner, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                mAction.onNext(resource.getData());
            } else if (resource.getStatus() == Resource.Status.LOADING) {
                mAction.doLoading(resource.getData());
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                mAction.doError(resource.getMsg());
            } else {
                Log.e("LiveData", "未知状态");
            }
        });
    }



    /**
     * success data自动判空 为空不回调
     *
     * @param owner
     * @param mAction
     */
    public void callbackNeverNull(@NonNull LifecycleOwner owner, LiveDataSimpleAction<T> mAction) {
        observe(owner, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS) {
                T data = resource.getData();
                if (data != null) {
                    mAction.onNext(data);
                } else {
                    Log.e("LiveData", "resource.getData() 为空");
                }
            } else if (resource.getStatus() == Resource.Status.LOADING) {
                mAction.doLoading(resource.getData());
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                mAction.doError(resource.getMsg());
            } else {
                Log.e("LiveData", "未知状态");
            }
        });
    }


    /*--------快速赋值-------------------------------------------*/

    public void setSuccess(@Nullable T data) {
        setValue(Resource.success(data));
    }

    public void setLoading(@Nullable T data) {
        setValue(Resource.loading(data));
    }

    public void setError(String message) {
        setValue(Resource.error(message));
    }

    public void setError(String message, int code) {
        setValue(Resource.error(code, message));
    }

    // post系列方法为在子线程中调用
    public void postSuccess(@Nullable T data) {
        postValue(Resource.success(data));
    }

    public void postLoading(@Nullable T data) {
        postValue(Resource.loading(data));
    }

    public void postError(String message) {
        postValue(Resource.error(message));
    }

    public void postError(String message, int code) {
        postValue(Resource.error(code, message));
    }
}
