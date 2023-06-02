package com.jay.core.mvvm.model;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ObjectUtils;
import com.jay.core.mvvm.model.model.BaseViewModel;
import com.jay.core.util.RefreshUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * create by hj on 2021/4/19
 **/
public class ViewModelFactoryUtils {
    private static IViewModelStatus iStatus;

    /**
     * 扩展公共事件
     *
     * @param status
     */
    public static void registerVieModelStatus(IViewModelStatus status) {
        iStatus = status;
    }

    public interface IViewModelStatus {
        void doStatusEvent(@Nullable Activity activity, ViewModelStatus status, SmartRefreshLayout mRefresh);
    }

    public static <ViewModel extends BaseViewModel> ViewModel getViewModel(FragmentActivity activity,
                                                                           @NonNull Class<ViewModel> modelClass,
                                                                           SmartRefreshLayout mRefresh) {
        ViewModel t = new ViewModelProvider(activity).get(modelClass);
        t.getStatusLiveData().doObserver(activity, data -> {
            if (iStatus != null) {
                iStatus.doStatusEvent(activity, data, mRefresh);
            }
            doStatusEventDefault(activity, data, mRefresh);
        });
        return t;
    }

    private static void doStatusEventDefault(Activity activity, ViewModelStatus status, SmartRefreshLayout mRefresh) {
        switch (status.getStatus()) {
            case ViewModelStatus.FINISH:
                if (activity != null) {
                    activity.finish();
                }
                break;
            case ViewModelStatus.STOP_REFRESH:
                if (mRefresh != null) {
                    RefreshUtil.stopRefreshOrLoad(mRefresh);
                }
                break;
            case ViewModelStatus.STOP_LOAD_MORE_NO_DATA:
                if (mRefresh != null) {
                    RefreshUtil.noData(mRefresh);
                }
                break;
        }
    }

    public static <ViewModel extends BaseViewModel> ViewModel getViewModel(Fragment fragment,
                                                                           @NonNull Class<ViewModel> modelClass,
                                                                           SmartRefreshLayout mRefresh) {
        ViewModel t = new ViewModelProvider(fragment).get(modelClass);
        t.getStatusLiveData().doObserver(fragment, data -> {
            if (iStatus != null) {
                iStatus.doStatusEvent(fragment.getActivity(), data, mRefresh);
            }
            doStatusEventDefault(fragment.getActivity(), data, mRefresh);
        });
        return t;
    }
}
