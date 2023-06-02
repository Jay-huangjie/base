package com.jay.base;

import android.util.Log;

import com.jay.base.viewmodel.AppViewModel;
import com.jay.core.http.listener.HttpClientListener;

import java.util.HashMap;
import java.util.Map;

/**
 * created by hj on 2023/5/11.
 */
public class MainViewModel extends AppViewModel {

    public void getConfig() {
        Map<String, String> params = new HashMap<>();
        params.put("c_number", "haizi");
        params.put("type", "config");
        params.put("packageName", "com.ape.apefather");

    }

    public void getTime() {
        getClient().clientTransformString(api().getTime("http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp"), new HttpClientListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("HJ", s);
            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }

}
