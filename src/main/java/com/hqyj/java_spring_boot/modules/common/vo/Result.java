package com.hqyj.java_spring_boot.modules.common.vo;

public class Result<T> {
    //返回状态码，如成功和失败；SUCCESS（200），FAILD（500）
    private int status;

    //返回信息
    private String massage;

    //返回对象
    private T object;

    public Result() {
    }

    public Result(int status, String massage) {
        this.status = status;
        this.massage = massage;
    }

    public Result(int status, String massage, T object) {
        this.status = status;
        this.massage = massage;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    //使用枚举来表示状态
    public enum ResultStatus{
        SUCCESS(200),FAILD(500);
        public int status;
        ResultStatus(int status) {
            this.status = status;
        }
    }
}
