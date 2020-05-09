package com.ccooy.expandablerecyclerview.stockdemo;

/**
 * 子项数据的实体类
 */
public class DailyRecordEntity {

    private String type;
    private String money;

    public DailyRecordEntity(String type, String money) {
        this.money = money;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
