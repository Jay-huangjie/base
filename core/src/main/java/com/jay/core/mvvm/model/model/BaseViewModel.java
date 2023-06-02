package com.jay.core.mvvm.model.model;

import androidx.lifecycle.ViewModel;
import com.jay.core.http.HttpManager;
import com.jay.core.http.conver.BaseClient;
import com.jay.core.mvvm.model.ViewModelStatus;
import com.jay.core.mvvm.model.livedata.BaseLiveData;

/**
 * create by hj on 2020/11/23
 * ViewModel基类
 * viewmodel设计原则：只负责管理UI的数据,永遠不要持有view或活動相關
 * 如果只是简单的网络请求,可以直接使用viewModel来进行网络请求 见：[execute]
 * 如果涉及到数据库等复杂的情况，就需要分离出一个[BaseRepository]类来管理，避免model逻辑太复杂
 * repository封装的好还可以用来进行数据复用
 * 经测试LiveData能接收null回调,不管
 **/
public abstract class BaseViewModel extends ViewModel implements IBaseModel {
    private final BaseLiveData<ViewModelStatus> mStatusLiveData = new BaseLiveData<>();

    private BaseClient mClient;

    public BaseLiveData<ViewModelStatus> getStatusLiveData() {
        return mStatusLiveData;
    }

    public BaseClient getClient() {
        mClient = HttpManager.getHttpConfig().getClient();
        return mClient;
    }

    @Override
    public void showLoading(String value) {
        mStatusLiveData.setValue(ViewModelStatus.loading(value));
    }

    @Override
    public void hideLoading() {
        mStatusLiveData.setValue(ViewModelStatus.removeLoading());
    }

    @Override
    public void finishActivity() {
        mStatusLiveData.setValue(ViewModelStatus.finish());
    }

    @Override
    public void stopRefreshOrLoadMore() {
        mStatusLiveData.setValue(ViewModelStatus.stopRefreshOrLoadMore());
    }

    @Override
    public void stopLoadMoreAndNoMoreData() {
        mStatusLiveData.setValue(ViewModelStatus.stopLoadMoreNoData());
    }

    @Override
    public void checkLogin(String message) {
        mStatusLiveData.setValue(ViewModelStatus.checkLogin(message));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mClient != null) {
            mClient.dispose();
        }
    }
}
