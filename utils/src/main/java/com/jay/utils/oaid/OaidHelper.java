package com.jay.utils.oaid;

import android.content.Context;
import android.text.TextUtils;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;

/**
 * created by hj on 2023/6/14.
 */
public class OaidHelper {

    private static OaidHelper mHelper;
    private iOaidCache cache;
    private OaidResultCallback callback;
    private Context context;

    private OaidHelper() {

    }

    public static OaidHelper get() {
        if (mHelper == null) {
            mHelper = new OaidHelper();
        }
        return mHelper;
    }

    public static void init(Context context,
                            iOaidCache cache,
                            OaidResultCallback callback) {
        OaidHelper oaidHelper = get();
        oaidHelper.setCache(cache);
        oaidHelper.setCallback(callback);
        oaidHelper.setContext(context);
        oaidHelper.initOaid();
    }

    private void setCache(iOaidCache cache) {
        this.cache = cache;
    }

    private void setCallback(OaidResultCallback callback) {
        this.callback = callback;
    }

    private void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public iOaidCache getCache() {
        if (cache == null) {
            throw new NullPointerException("请先init");
        }
        return cache;
    }

    public String getOaid() {
        return getCache().getOaid();
    }

    private void initOaid() {
        String oaid = getCache().getOaid();
        if (TextUtils.isEmpty(oaid)) {
            int code = MdidSdkHelper.InitSdk(context, true, new IIdentifierListener() {
                @Override
                public void OnSupport(boolean b, IdSupplier idSupplier) {
                    if (idSupplier == null) {
                        callback.onError("配置文件为空");
                        return;
                    }
                    if (b) {
                        String i = idSupplier.getOAID();
                        if (TextUtils.isEmpty(i)) {
                            callback.onError("oaid获取成功,但数据是空的");
                            return;
                        }
                        callback.onSuccess(i);
                        getCache().saveOaid(i);
                    } else {
                        callback.onError("不支持该手机");
                    }
                }
            });
            if (code == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT) {//不支持的设备
                callback.onError("不支持的设备");
            } else if (code == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE) {//加载配置文件出错
                callback.onError("加载配置文件出错");
            } else if (code ==
                    ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {//不支持的设备厂商
                callback.onError("不支持的设备厂商");
            } else if (code == ErrorCode.INIT_ERROR_RESULT_DELAY) {//获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程
                callback.onError("获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程");
            } else if (code == ErrorCode.INIT_HELPER_CALL_ERROR) {//反射调出错
                callback.onError("反射调出错");
            }
        } else {
            callback.onSuccess(oaid);
        }
    }


}
