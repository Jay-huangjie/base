package com.jay.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jay.base.bean.TestBean;
import com.jay.base.databinding.ActivityMainBinding;
import com.jay.core.mvvm.BaseActivity;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initEvent() {
       ClassLoader.getSystemClassLoader().clearAssertionStatus();;
    }

}