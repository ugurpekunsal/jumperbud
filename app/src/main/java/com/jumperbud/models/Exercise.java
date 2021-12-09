package com.jumperbud.models;

import java.util.Date;

public class Exercise {
    public long id;
    public int calories, duration, jumps;
    public Date date;

    public Exercise() {
    }

    public Exercise(long id, int calories, int duration, int jumps, Date date) {
        this.id = id;
        this.calories = calories;
        this.duration = duration;
        this.jumps = jumps;
        this.date = date;
    }
}
