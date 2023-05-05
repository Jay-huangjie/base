package com.jay.keyboard;

import android.os.Build;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * created by hj on 2023/5/4.
 */
public class EditUtils {

    /**
     * EditText 不弹出软键盘
     * 但是会不显示光标
     */
    public static void disableShowInput(EditText editText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
