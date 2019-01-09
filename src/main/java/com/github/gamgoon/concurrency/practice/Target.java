package com.github.gamgoon.concurrency.practice;

public class Target {
    private String deviceId;
    private String mSeq;
    private String deviceGbn;
    private String pushKey;
    private String appVersion;

    @Override
    public String toString() {
        return "Target{" +
                "deviceId='" + deviceId + '\'' +
                ", mSeq='" + mSeq + '\'' +
                ", deviceGbn='" + deviceGbn + '\'' +
                ", pushKey='" + pushKey + '\'' +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getmSeq() {
        return mSeq;
    }

    public void setmSeq(String mSeq) {
        this.mSeq = mSeq;
    }

    public String getDeviceGbn() {
        return deviceGbn;
    }

    public void setDeviceGbn(String deviceGbn) {
        this.deviceGbn = deviceGbn;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
