package com.jay.core.mvvm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.jay.core.mvvm.model.ViewModelFactoryUtils;
import com.jay.core.mvvm.model.model.BaseViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * create by hj on 2020/12/30
 * <p>
 * Mvvm框架基类 抛弃Mvp模式 过度到Mvvm
 **/
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected Context context;
    public Bundle savedInstanceState;
    public T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (isPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//竖屏
        }
        this.context = this;
        this.savedInstanceState = savedInstanceState;
        initWindowConfig();
        int rootViewId = getRootViewId();
        mBinding = DataBindingUtil.setContentView(this, rootViewId);
        initEvent();
        onCreateEnd();
    }

    public void onCreateEnd() {
    }


    public void initWindowConfig() {
    }

    public boolean isPortrait() {
        return true;
    }

    public void starActivity(Class<?> c) {
        Intent intent = new Intent(context, c);
        startActivity(intent);
    }

    public void setStatusViewColor(int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != -1) {
                getWindow().setStatusBarColor(statusColor);
            }
        }
    }

    public void back(View view) {
        finish();
    }

    public int getResColor(int color) {
        return ContextCompat.getColor(context, color);
    }


    public <ViewModel extends BaseViewModel> ViewModel getViewModel(@NonNull Class<ViewModel> modelClass) {
        return ViewModelFactoryUtils.getViewModel(this, modelClass, null);
    }

    public <ViewModel extends BaseViewModel> ViewModel getViewModel(@NonNull Class<ViewModel> modelClass, SmartRefreshLayout mRefresh) {
        return ViewModelFactoryUtils.getViewModel(this, modelClass, mRefresh);
    }

    public abstract int getRootViewId();

    public abstract void initEvent();
}
