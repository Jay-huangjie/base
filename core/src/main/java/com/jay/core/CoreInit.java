package com.jay.core;

import com.jay.core.http.HttpManager;
import com.jay.core.mvvm.model.ViewModelFactoryUtils;

/**
 * created by hj on 2023/5/11.
 * 框架初始化管理
 */
public class CoreInit {

    //初始化网络框架（必须）
    public static void initHttpConfig(HttpManager.Build build) {
        HttpManager.init(build);
    }

    //初始化全局状态处理
    public static void initPublicStatus(ViewModelFactoryUtils.IViewModelStatus status) {
        ViewModelFactoryUtils.registerVieModelStatus(status);
    }


}
