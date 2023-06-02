package com.jay.core.mvvm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * Created by hj on 2017/1/22.
 * dialog基类
 */
public abstract class BaseDialog extends Dialog {
    public Context context;
    protected View view;
    private float hPercent;
    private float wPercent;
    public int height; //屏幕高度
    public int width; //屏幕宽度
    public OnDialogClickListener mDialogClickListener;

    private SparseArray<View> mViews = new SparseArray<View>();

    /**
     * 跟布局背景，用来设置圆角
     */
    private int resID;


    public BaseDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public float getHeightPercent() {
        return hPercent;
    }

    /**
     * 设置弹窗高度百分比(占整个屏幕)
     */
    public BaseDialog setHeightPercent(float hPercent) {
        this.hPercent = hPercent;
        return this;
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutID();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int layoutID = getLayoutID();
        if (layoutID != 0) {
            view = inflater.inflate(layoutID, null);
        } else {
            throw new NullPointerException(this.getClass().getSimpleName() + "没有设置资源文件");
        }
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        height = d.heightPixels;
        width = d.widthPixels;
        if (getWidthPercent() != 0.0f) {
            lp.width = (int) (width * wPercent);
        } else {
            lp.width = (int) (width * 0.9);
        }
        if (getHeightPercent() != 0.0f) {
            lp.height = (int) (height * hPercent);
        }
        if (resID != 0) {
            dialogWindow.setBackgroundDrawableResource(resID);
        }
        dialogWindow.setAttributes(lp);
        initView();
        initEvent();
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public <V extends View> V getViewByID(@IdRes int viewId) {
        V Object = (V) mViews.get(viewId);
        if (Object == null) {
            try {
                Object = (V) view.findViewById(viewId);
            } catch (NullPointerException e) {
                throw new NullPointerException("请确认资源文件不为空");
            }
            mViews.put(viewId, Object);
        }
        return Object;
    }


    public void setOnClickListener(View.OnClickListener listener, View... views) {
        if (listener == null) {
            throw new NullPointerException("BaseFragment：#.setOnClickListener()方法参数 (View.OnClickListener listener) 为null；#附：监听都不给,你想做啥?");
        }
        if (views == null) {
            throw new NullPointerException("BaseFragment：#.setOnClickListener()方法参数 (View... views) 为null；#附：View都不给,你想给谁设置点击事件啊?");
        }
        for (View v : views) {
            v.setOnClickListener(listener);
        }
    }

    /**
     * 获取当前布局View
     *
     * @return
     */
    public View getView() {
        return view;
    }

    public void showShortToast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前弹窗设置的宽度百分比
     *
     * @return
     */
    public float getWidthPercent() {
        return wPercent;
    }

    /**
     * 设置宽度百分比
     *
     * @param widthPercent
     * @return
     */
    public BaseDialog setWidthpercent(float widthPercent) {
        this.wPercent = widthPercent;
        return this;
    }

    public void setBackgroundWindowTransparent() {
        ColorDrawable drawable = new ColorDrawable();
        drawable.setColor(Color.parseColor("#00000000"));
        getWindow().setBackgroundDrawable(drawable);
    }

    /**
     * @return 获取设置的背景
     */
    public int getRootResID() {
        return resID;
    }

    /**
     * 设置弹窗Window的背景,一般用于设置圆角
     *
     * @param resID
     * @return
     */
    public BaseDialog setRootResID(int resID) {
        this.resID = resID;
        return this;
    }

    /**
     * 弹窗的默认接口
     */
    public interface OnDialogClickListener {
        void Confirm();

        void Cancel();
    }

}
