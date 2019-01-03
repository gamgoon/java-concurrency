package com.github.gamgoon.concurrency.ch03.cache;

import java.util.Date;

public class CacheItem {
    private String command;
    private String response;
    private Date creationDate;
    private Date accessDate;

    public String getCommand() {
        return command;
    }

    public String getResponse() {
        return response;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public CacheItem(String command, String response) {
        this.command = command;
        this.response = response;
        this.creationDate = new Date();
        this.accessDate = new Date();
    }
}
