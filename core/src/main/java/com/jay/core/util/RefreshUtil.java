package com.jay.core.util;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/**
 * create by hj on 2020/7/17
 **/
public class RefreshUtil {

    /*
     * 结束刷新或加载，自动判断
     * */
    public static void stopRefreshOrLoad(SmartRefreshLayout refreshLayout) {
        if (refreshLayout == null) return;
        if (refreshLayout.getState() == RefreshState.Loading) {
            refreshLayout.finishLoadMore();
        }
        if (refreshLayout.getState() == RefreshState.Refreshing) {
            refreshLayout.finishRefresh();
        }
    }

    public static void noData(SmartRefreshLayout refreshLayout) {
        if (refreshLayout == null) return;
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    public static boolean isRefresh(SmartRefreshLayout refreshLayout) {
        if (refreshLayout == null) return false;
        return refreshLayout.getState() == RefreshState.Refreshing;
    }
}
