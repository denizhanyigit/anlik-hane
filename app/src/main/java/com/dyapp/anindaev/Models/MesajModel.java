package com.dyapp.anindaev.Models;

public class MesajModel {

    private String from;
    private String text;
    private String date;

    public MesajModel(String from, String text,String date) {
        this.from = from;
        this.text = text;
        this.date=date;
    }
    public MesajModel()
    {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MesajModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
