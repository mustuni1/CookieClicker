package com.github.mustuni1.cookieclicker;

/**
 * Created by test on 2/6/16.
 */
public class Book {
    private long time;
    private int score = 0;
    private boolean onOrOff = true;

    public Book(){}

    public Book(long time, int score) {
        super();
        this.time = time;
        this.score = score;
    }

    //getters & setters

    public void addTime(long t) {
        if(onOrOff) {
            onOrOff = false;
            this.time = t;
        }
        else {
            onOrOff = true;
            long diff = t - this.time;
            score += diff / 60;
            this.time = t;
        }
    }

    public void setTime(long t) {
        this.time = t;
    }

    public long getTime() {
        return this.time;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getOnOff() {
        return this.onOrOff;
    }

    public String toString() {
        return "Time: " + time + "\nScore: " + score + "\nOn/Off: " + onOrOff;
    }
}
