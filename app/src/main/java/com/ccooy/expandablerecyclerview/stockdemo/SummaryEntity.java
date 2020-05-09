package com.ccooy.expandablerecyclerview.stockdemo;

public class SummaryEntity {
    private String time;
    private String amount;
    private String count;
    private String discount_num;
    private String discount_amount;

    public SummaryEntity(String time, String amount, String count, String discount_num, String discount_amount) {
        this.time = time;
        this.amount = amount;
        this.count = count;
        this.discount_num = discount_num;
        this.discount_amount = discount_amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDiscount_num() {
        return discount_num;
    }

    public void setDiscount_num(String discount_num) {
        this.discount_num = discount_num;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }
}
