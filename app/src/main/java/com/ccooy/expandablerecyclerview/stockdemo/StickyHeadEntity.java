package com.ccooy.expandablerecyclerview.stockdemo;

public class StickyHeadEntity<T>{

    private final int itemType;

    private T data;

    private String stickyHeadName;

    public StickyHeadEntity(T data, int itemType, String stickyHeadName) {
        this.data = data;
        this.itemType = itemType;
        this.stickyHeadName = stickyHeadName;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStickyHeadName(String stickyHeadName) {
        this.stickyHeadName = stickyHeadName;
    }

    public T getData() {
        return data;
    }

    public String getStickyHeadName() {
        return stickyHeadName;
    }

    public int getItemType() {
        return itemType;
    }
}
