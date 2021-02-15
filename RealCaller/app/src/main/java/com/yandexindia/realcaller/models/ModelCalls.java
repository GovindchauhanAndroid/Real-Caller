package com.yandexindia.realcaller.models;

public class ModelCalls {
    public ModelCalls(String name, String number, String duration, String date) {
        this.name = name;
        this.number = number;
        this.duration = duration;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String name, number, duration, date;
}
