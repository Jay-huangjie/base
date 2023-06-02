package com.jay.core.mvvm.model;

/**
 * create by hj on 2020/11/23
 * 状态
 **/
public class ViewModelStatus {
    /**
     * 定义的一些动作常量
     */
    public static final int CONTENT = 101;
    public static final int TOAST = 102;
    public static final int ERROR = 103;
    public static final int LOADING = 104;
    public static final int REMOVE = 105;
    public static final int FINISH = 106;
    public static final int STOP_REFRESH = 107;
    public static final int STOP_LOAD_MORE_NO_DATA = 108;
    public static final int AUTO_REFRESH = 109;
    public static final int CHECK_LOGIN = 100;

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ViewModelStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ViewModelStatus loading(String message) {
        return new ViewModelStatus(LOADING, message);
    }

    public static ViewModelStatus loading() {
        return new ViewModelStatus(LOADING, "加载中");
    }

    public static ViewModelStatus removeLoading() {
        return new ViewModelStatus(REMOVE, "");
    }

    public static ViewModelStatus finish() {
        return new ViewModelStatus(FINISH, "");
    }

    public static ViewModelStatus stopRefreshOrLoadMore() {
        return new ViewModelStatus(STOP_REFRESH, "");
    }

    public static ViewModelStatus stopLoadMoreNoData() {
        return new ViewModelStatus(STOP_LOAD_MORE_NO_DATA, "");
    }

    public static ViewModelStatus autoRefresh() {
        return new ViewModelStatus(AUTO_REFRESH, "");
    }

    public static ViewModelStatus checkLogin(String message) {
        return new ViewModelStatus(CHECK_LOGIN, message);
    }

}
