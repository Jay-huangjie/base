package com.jay.core.http.conver;

import com.jay.core.http.listener.HttpClientListener;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * created by hj on 2023/6/2.
 * 上层需要实现此抽象类来设置具体的请求方式
 * 实现方式见{@link com.jay.core.http.impl.HttpClient}
 */
public abstract class BaseClient {
    private final CompositeDisposable compositeDisposable;

    public BaseClient() {
        this.compositeDisposable = new CompositeDisposable();
    }

    /**
     * 自动解析,传入实体类
     */
    public abstract <D, T extends IResult<D>> void client(Observable<T> observable, final HttpClientListener<D> listener);

    /**
     * 返回原始请求的json并解析到指定类
     */
    public abstract <T> void clientTransformResult(Observable<T> observable, final HttpClientListener<T> listener);

    /**
     * 将返回的原始请求转化为String返回
     *
     * @param observable
     * @param listener
     */
    public abstract void clientTransformString(Observable<Object> observable, final HttpClientListener<String> listener);

    /**
     * 获取ApiService
     * @return
     */
    public abstract <T> T createApi();

    /**
     * 添加请求体
     */
    public void addDispose(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    /**
     * 取消请求
     */
    public void dispose() {
        compositeDisposable.clear();
    }


}
