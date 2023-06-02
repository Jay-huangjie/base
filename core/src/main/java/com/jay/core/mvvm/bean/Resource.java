package com.jay.core.mvvm.bean;

/**
 * create by hj on 2022/4/15
 **/
public class Resource<T> {
    private T data;
    private String msg;
    private int code;
    private Status status;

    public Resource() {
        //默认状态是成功
        status = Status.SUCCESS;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public static Resource success() {
        Resource resource = new Resource<>();
        resource.setStatus(Status.SUCCESS);
        return resource;
    }

    public static <T> Resource<T> success(T data) {
        Resource<T> resource = new Resource<>();
        resource.setStatus(Status.SUCCESS);
        resource.setData(data);
        return resource;
    }

    public static <T> Resource<T> loading(T data) {
        Resource<T> resource = new Resource<>();
        resource.setStatus(Status.LOADING);
        resource.setData(data);
        return resource;
    }

    public static <T> Resource<T> error(String msg) {
        return error(-1, msg);
    }

    public static <T> Resource<T> error(int code, String msg) {
        Resource<T> resource = new Resource<>();
        resource.setStatus(Status.ERROR);
        resource.setCode(code);
        resource.setMsg(msg);
        return resource;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
