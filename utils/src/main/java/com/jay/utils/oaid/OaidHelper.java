package com.jay.utils.oaid;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bun.miitmdid.core.InfoCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;
import com.bun.miitmdid.pojo.IdSupplierImpl;


/**
 * created by hj on 2023/6/14.
 */
public class OaidHelper implements IIdentifierListener {

    private static final String TAG = "OaidHelper";

    private static OaidHelper mHelper;
    private onIdsCallback callback;
    private Context context;
    //证书内容是否加载成功
    private boolean isCertInit = false;
    private String assetsCertFileName;
    private String serviceCert; //服务器下发的证书信息

    private OaidHelper() {

    }

    public static OaidHelper get() {
        if (mHelper == null) {
            mHelper = new OaidHelper();
        }
        return mHelper;
    }

    public static void init(Context context,
                            String assetsCertFileName, onIdsCallback callback) {
        boolean archSupport = OaidUtils.isArchSupport();
        if (archSupport) {
            //这里可能lib包会加载失败，try一下
            try {
                System.loadLibrary("msaoaidsec");
                OaidHelper oaidHelper = get();
                oaidHelper.setContext(context);
                oaidHelper.setCallback(callback);
                oaidHelper.setAssetsCertFileName(assetsCertFileName);
                //在Application中先从assets中加载一遍证书，看是否有效
                oaidHelper.initCert(OaidUtils.loadPemFromAssetFile(context, assetsCertFileName));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } else {
            Log.w(TAG, "当前架构不支持Oaid");
        }
    }


    private void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    private void setAssetsCertFileName(String assetsCertFileName) {
        this.assetsCertFileName = assetsCertFileName;
    }

    public void setCallback(onIdsCallback callback) {
        this.callback = callback;
    }

    /**
     * 从服务器器端加载最新的证书
     *
     * @param serviceCert
     */
    public void setServiceCert(String serviceCert) {
        this.serviceCert = serviceCert;
        //加载一遍证书，如果init的时候证书加载失败就会重新去加载一遍了
        initCert(serviceCert);
    }

    public String getOaidForCache() {
        if (callback != null) {
            return callback.getOaid();
        }
        return "";
    }

    /**
     * 初始化证书
     *
     * @return
     */
    private void initCert(String certContent) {
        if (isCertInit) {
            return;
        }
        if (TextUtils.isEmpty(certContent)) {
            Log.w(TAG, "证书为空，默认从Assets中加载");
            certContent = OaidUtils.loadPemFromAssetFile(context, assetsCertFileName);
        }
        if (TextUtils.isEmpty(certContent)) {
            Log.w(TAG, "证书为空");
            return;
        }
        isCertInit = MdidSdkHelper.InitCert(context, certContent);
        if (!isCertInit) {
            Log.w(TAG, "证书加载失败");
        } else {
            loadOaid();
        }
    }

    //加载OAID sdk
    public void loadOaid() {
        if (callback != null && !TextUtils.isEmpty(callback.getOaid())) {
            Log.w(TAG, "OAID已缓存，无需重新获取");
            return;
        }
        if (!isCertInit) {
            initCert(serviceCert);
        }
        if (!isCertInit) {
            Log.e(TAG, "证书加载失败");
            return;
        }
        int code = 0;
        try {
            code = MdidSdkHelper.InitSdk(context, true, true, false, false, this);
        } catch (Error error) {
            error.printStackTrace();
        }
        IdSupplierImpl unsupportedIdSupplier = new IdSupplierImpl();
        if (code == InfoCode.INIT_ERROR_CERT_ERROR) {                         // 证书未初始化或证书无效，SDK内部不会回调onSupport
            // APP自定义逻辑
            Log.w(TAG, "cert not init or check not pass");
            onSupport(unsupportedIdSupplier);
        } else if (code == InfoCode.INIT_ERROR_DEVICE_NOSUPPORT) {             // 不支持的设备, SDK内部不会回调onSupport
            // APP自定义逻辑
            Log.w(TAG, "device not supported");
            onSupport(unsupportedIdSupplier);
        } else if (code == InfoCode.INIT_ERROR_LOAD_CONFIGFILE) {            // 加载配置文件出错, SDK内部不会回调onSupport
            // APP自定义逻辑
            Log.w(TAG, "failed to load config file");
            onSupport(unsupportedIdSupplier);
        } else if (code == InfoCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {      // 不支持的设备厂商, SDK内部不会回调onSupport
            // APP自定义逻辑
            Log.w(TAG, "manufacturer not supported");
            onSupport(unsupportedIdSupplier);
        } else if (code == InfoCode.INIT_ERROR_SDK_CALL_ERROR) {             // sdk调用出错, SSDK内部不会回调onSupport
            // APP自定义逻辑
            Log.w(TAG, "sdk call error");
            onSupport(unsupportedIdSupplier);
        } else if (code == InfoCode.INIT_INFO_RESULT_DELAY) {             // 获取接口是异步的，SDK内部会回调onSupport
            Log.i(TAG, "result delay (async)");
        } else if (code == InfoCode.INIT_INFO_RESULT_OK) {                  // 获取接口是同步的，SDK内部会回调onSupport
            Log.i(TAG, "result ok (sync)");
        } else {
            // sdk版本高于DemoHelper代码版本可能出现的情况，无法确定是否调用onSupport
            // 不影响成功的OAID获取
            Log.w(TAG, "getDeviceIds: unknown code: " + code);
        }
    }


    @Override
    public void onSupport(IdSupplier supplier) {
        if (supplier == null) {
            Log.w(TAG, "onSupport: supplier is null");
            return;
        }
        if (callback == null) {
            Log.w(TAG, "onSupport: 未设置callback回调");
            return;
        }
        String oaid;
        // 获取Id信息
        // 注：IdSupplier中的内容为本次调用MdidSdkHelper.InitSdk()的结果，不会实时更新。 如需更新，需调用MdidSdkHelper.InitSdk()
        boolean isSupported = supplier.isSupported();
        if (isSupported) {
            oaid = supplier.getOAID();
            callback.onIdsValid(oaid);
            Log.w(TAG, "oaid获取成功:" + oaid);
        } else {
            Log.w(TAG, "不支持获取OAID");
        }
    }
}
