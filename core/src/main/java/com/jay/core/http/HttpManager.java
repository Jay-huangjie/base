package com.jay.core.http;

import com.jay.core.http.conver.BaseClient;
import com.jay.core.http.impl.HttpClient;

/**
 * created by hj on 2023/5/10.
 */
public class HttpManager {

    private static HttpConfig httpConfig;

    public static void init(Build build) {
        httpConfig = build.create();
    }

    public static HttpConfig getHttpConfig() {
        if (httpConfig == null) {
            throw new NullPointerException("请初始化HttpConfig");
        }
        return httpConfig;
    }

    public static class HttpConfig {
        private String baseUrl;
        //网络请求上层封装
        private BaseClient mClient;

        public String getBaseUrl() {
            return baseUrl;
        }

        public BaseClient getClient() {
            if (mClient == null) {
                mClient = new HttpClient();
            }
            return mClient;
        }

    }

    public static class Build {
        private String baseUrl;
        private BaseClient client;

        public Build setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        //设置请求框架方式，不设置默认使用HttpClient
        public Build setBaseClient(BaseClient client) {
            this.client = client;
            return this;
        }

        public HttpConfig create() {
            HttpConfig config = new HttpConfig();
            config.baseUrl = baseUrl;
            config.mClient = client;
            return config;
        }
    }

}
