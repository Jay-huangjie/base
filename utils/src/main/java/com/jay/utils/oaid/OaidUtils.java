package com.jay.utils.oaid;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * created by hj on 2023/6/28.
 */
public class OaidUtils {

    /**
     * 从asset文件读取证书内容
     *
     * @param context
     * @param assetFileName
     * @return 证书字符串
     */
    public static String loadPemFromAssetFile(Context context, String assetFileName) {
        try {
            InputStream is = context.getAssets().open(assetFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //检测是不是x86
    public static boolean isArchSupport() {
        String value = null;
        boolean isArchSupport = true;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method get = clazz.getMethod("get", String.class, String.class);
            Object invoke = get.invoke(clazz, "ro.product.cpu.abi", "");
            if (invoke != null) {
                value = invoke.toString();
            }
            if (value == null) {
                return false;
            }
            isArchSupport = !value.contains("x86");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return isArchSupport;
    }

}
