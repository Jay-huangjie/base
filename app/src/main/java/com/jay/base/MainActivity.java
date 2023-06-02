package com.jay.base;

import android.app.Activity;
import android.os.Bundle;

import com.jay.base.databinding.ActivityMainBinding;
import com.jay.core.mvvm.BaseActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initEvent() {
        MainViewModel model = getViewModel(MainViewModel.class);
        model.getTime();
    }
}