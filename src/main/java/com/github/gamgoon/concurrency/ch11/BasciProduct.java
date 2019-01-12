package com.github.gamgoon.concurrency.ch11;

public class BasciProduct implements Comparable<BasciProduct> {
    private String id;
    private String asin;
    private String title;

    @Override
    public String toString() {
        return "BasciProduct{" +
                "id='" + id + '\'' +
                ", asin='" + asin + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(BasciProduct o) {
        System.out.println(o.getAsin().compareTo(getAsin()));
        return o.getAsin().compareTo(getAsin());
    }

    @Override
    public int hashCode() {
        return asin.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        BasciProduct other = (BasciProduct) obj;
        return asin.equals(other.asin);
    }
}
