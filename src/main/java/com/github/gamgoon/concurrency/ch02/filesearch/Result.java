package com.github.gamgoon.concurrency.ch02.filesearch;

public class Result {
    private String path;
    private boolean found = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
