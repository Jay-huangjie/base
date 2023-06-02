package com.jay.core.mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.jay.core.mvvm.model.ViewModelFactoryUtils;
import com.jay.core.mvvm.model.model.BaseViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * create by hj
 * Fragment ViewModel基类
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected Context context;

    private View rootView;

    public T mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(getRootViewId(), container, false);
        mBinding = DataBindingUtil.bind(rootView);
        onCreateViewEnd();
        return rootView;
    }

    public void onCreateViewEnd() {
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    public abstract int getRootViewId();

    public abstract void initData();

    /*------------------------获取ViewModel----------------------------------------------------------------*/

    //fragment获取ViewModel
    public <ViewModel extends BaseViewModel> ViewModel getViewModel(@NonNull Class<ViewModel> modelClass) {
        return getViewModel(modelClass, null);
    }

    //获取Activity的ViewModel
    public <ViewModel extends BaseViewModel> ViewModel getActivityViewModel(@NonNull Class<ViewModel> modelClass) {
        return getActivityViewModel(modelClass, null);
    }

    //获取能刷新的ViewModel
    public <ViewModel extends BaseViewModel> ViewModel getViewModel(@NonNull Class<ViewModel> modelClass, SmartRefreshLayout refreshLayout) {
        return ViewModelFactoryUtils.getViewModel(this, modelClass, refreshLayout);
    }

    public <ViewModel extends BaseViewModel> ViewModel getActivityViewModel(@NonNull Class<ViewModel> modelClass, SmartRefreshLayout refreshLayout) {
        return ViewModelFactoryUtils.getViewModel(getActivity(), modelClass, refreshLayout);
    }

}
