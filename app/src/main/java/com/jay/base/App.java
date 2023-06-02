package com.jay.base;

import android.app.Application;

import com.jay.core.CoreInit;
import com.jay.core.http.HttpManager;

/**
 * created by hj on 2023/5/11.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CoreInit.initHttpConfig(new HttpManager.Build()
                .setBaseUrl("http://ybb.zlfc.vip/"));
    }
}
