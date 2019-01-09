package com.github.gamgoon.concurrency.practice;

public class Result {
    private int success;
    private int fail;
    private String message;

    public Result(int success, int fail, String message) {
        this.success = success;
        this.fail = fail;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", fail=" + fail +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

}
