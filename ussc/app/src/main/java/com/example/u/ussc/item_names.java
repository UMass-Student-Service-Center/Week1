package com.example.u.ussc;

public class item_names {
    private String userid;
    private String title;
    private String image;
    private String descr;
    private String price;
    private String time;

    public item_names(){}

    public item_names(String user_id,String mtitle,String mimage,String mdecr,String mprice,String mtime){
        userid = user_id;
        title = mtitle;
        image =mimage;
        descr = mdecr;
        price = mprice;
        time = mtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}