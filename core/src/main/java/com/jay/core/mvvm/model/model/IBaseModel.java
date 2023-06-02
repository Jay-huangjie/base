package com.jay.core.mvvm.model.model;

/**
 * create by hj on 2020/11/23
 **/
public interface IBaseModel {
    public void showLoading(String value);
    public void hideLoading();
    public void finishActivity();
    public void stopRefreshOrLoadMore();//停止刷新或加载
    public void stopLoadMoreAndNoMoreData(); //提示没有更多数据了
    public void checkLogin(String message); //检查是否登录
}
