package com.cx.retrofitdemo.model;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ApiResult<T> {

    public T data=null;
    public int state=0;
    public String msg="";

    public ApiResult(){

    }

    public ApiResult(T data, int state, String msg) {
        this.data = data;
        this.state = state;
        this.msg = msg;
    }

    public ApiResult(T data) {
        this.data = data;
    }

}
