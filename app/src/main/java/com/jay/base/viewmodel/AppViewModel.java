package com.jay.base.viewmodel;

import com.jay.base.ApiService;
import com.jay.core.mvvm.model.model.BaseViewModel;


/**
 * created by hj on 2023/5/11.
 */
public class AppViewModel extends BaseViewModel {

    public ApiService api() {
        return getClient().createApi();
    }
}
