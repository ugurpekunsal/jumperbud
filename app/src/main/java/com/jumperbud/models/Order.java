package com.jumperbud.models;

import java.util.Date;

public class Order {
    public String userId;
    public String trainerId;
    public Date date;
    public boolean confirmed;

    public Order() {
    }

    public Order(String userId, String trainerId, Date date) {
        this.userId = userId;
        this.trainerId = trainerId;
        this.date = date;
        this.confirmed = false;
    }
}
