package com.jay.utils.oaid;

/**
 * created by hj on 2023/6/19.
 */
public interface OaidResultCallback {

    void onSuccess(String oaid);

    void onError(String error);

}
