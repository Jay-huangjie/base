package com.jay.base.bean;
import com.jay.core.http.conver.IResult;

/**
 * created by hj on 2023/5/11.
 */
public class Result<T> implements IResult<T> {
    private int code;
    private String msg;
    private T data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
