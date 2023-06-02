package com.jay.core.http.conver;

/**
 * created by hj on 2023/6/2.
 * 上层的最外层实体类需要集成它才能自动解析和判断
 */
public interface IResult<D> {
    D getData();

    String getMsg();

    int getCode();

}
