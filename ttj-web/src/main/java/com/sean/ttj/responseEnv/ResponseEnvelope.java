package com.sean.ttj.responseEnv;

/**
 * Created by tengdj on 2016/6/24.
 */
public class ResponseEnvelope<T> {

    private boolean success;
    private T opt;
    private String message;

    public ResponseEnvelope(boolean success,String message){
        this.success = success;
        this.message = message;
    }

    public ResponseEnvelope(boolean success,String message, T opt){
        this.success = success;
        this.message = message;
        this.opt = opt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getOpt() {
        return opt;
    }

    public void setOpt(T opt) {
        this.opt = opt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
