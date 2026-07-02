package com.example.helloworld.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guantong
 */
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    /**
     * 业务数据。
     * 大部分场景是 key-value 的 Map（走 {@link #data(String, Object)}），
     * 但某些接口（如 /user/menu）需要顶层 data 直接是数组/单值——
     * 走 {@link #data(Object)} 方法把它整体替换。
     */
    private Object data = new HashMap<String, Object>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 把构造方法私有
     * */
    public Result() {}

    /**
     *
     * 成功静态方法
     * */
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    /**
     * 失败静态方法
     * */
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }


    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Result data(String key, Object value){
        if (!(this.data instanceof Map)) {
            this.data = new HashMap<String, Object>();
        }
        ((Map<String, Object>) this.data).put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    /**
     * 把 data 整体替换为任意对象（数组、单值、DTO 等）。
     * 用于 /user/menu 这类要求 data 直接是数组的接口。
     */
    public Result data(Object value){
        this.setData(value);
        return this;
    }

}
