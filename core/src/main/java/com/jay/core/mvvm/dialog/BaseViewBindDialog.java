package com.jay.core.mvvm.dialog;

import android.content.Context;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.jay.core.mvvm.dialog.BaseDialog;

/**
 * create by hj on 2022/9/13
 **/
public abstract class BaseViewBindDialog<V extends ViewDataBinding> extends BaseDialog implements LifecycleEventObserver {

    public V mBinding;

    public BaseViewBindDialog(Context context) {
        this(context, 0);
    }

    public BaseViewBindDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseViewBindDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void initView() {
        if (context instanceof ComponentActivity) {
            ((ComponentActivity) context).getLifecycle().addObserver(this);
        }
        mBinding = DataBindingUtil.bind(view);
        doEvent();
    }

    @Override
    protected void initEvent() {

    }

    public abstract void doEvent();

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (Lifecycle.Event.ON_DESTROY == event) {
            if (isShowing()) {
                dismiss();
            }
            if (context instanceof ComponentActivity) {
                ((ComponentActivity) context).getLifecycle().removeObserver(this);
            }
        }
    }
}
