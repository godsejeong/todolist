package com.wark.todolist;

/**
 * Created by choi on 2017. 5. 13..
 */

public class Data {
    String title;
    String date;
    String time;

    public Data(String title, String date, String time){
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setTime(String time){
        this.time = time;
    }
}
