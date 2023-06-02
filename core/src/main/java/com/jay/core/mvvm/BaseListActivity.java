package com.jay.core.mvvm;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jay.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create by hj on 2021/1/16
 **/
public abstract class BaseListActivity<T,K extends ViewDataBinding> extends BaseActivity<K> {
    private List<T> mData = new ArrayList<>();

    private ListAdapter mAdapter;

    @Override
    public void initEvent() {
        mAdapter = new ListAdapter(mData);
        RecyclerView recyclerView = bindRecyclerView();
        if (recyclerView == null) {
            throw new NullPointerException("bindRecyclerView 不能为Null");
        }
        recyclerView.setLayoutManager(getLayoutManager());
        mAdapter.bindToRecyclerView(recyclerView);
        View emptyView = LayoutInflater.from(context).inflate(R.layout.empty_view, null, false);
        mAdapter.setEmptyView(emptyView);
        initUi();
    }

    protected abstract RecyclerView bindRecyclerView();

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }

    protected abstract int getAdapterLayout(); //设置适配器布局

    protected abstract void convertItem(BaseViewHolder helper, T item); //处理item逻辑

    protected abstract void initUi();

    public class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public ListAdapter(@Nullable List<T> data) {
            super(getAdapterLayout(), data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            convertItem(helper, item);
        }
    }

    /**
     * adapter刷新
     */
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void setData(List<T> newData) {
        mData.clear();
        if (newData != null)
            mData.addAll(newData);
    }

    public List<T> getListData() {
        return mData;
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }
}
