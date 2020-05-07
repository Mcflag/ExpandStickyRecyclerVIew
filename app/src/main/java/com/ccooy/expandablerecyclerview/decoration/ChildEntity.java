package com.ccooy.expandablerecyclerview.decoration;

/**
 * 子项数据的实体类
 */
public class ChildEntity {

    private String type;
    private String money;

    public ChildEntity(String type, String money) {
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
