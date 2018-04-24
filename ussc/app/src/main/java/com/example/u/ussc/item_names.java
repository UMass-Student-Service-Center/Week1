package com.example.u.ussc;

public class item_names {
    private String userid;
    private String title;
    private String image;
    private String descr;
    private String price;
    private String time;
    //private ProfileItem name;
    private String name;
    private String user_image;
    private String item_key;
    private String item_type;

    public item_names(){}

    public item_names(String key, String user_id,String user_n,String type ,String u_image ,String mtitle,String mimage,String mdecr,String mprice,String mtime){
        item_key = key;
        userid = user_id;
        name = user_n;
        user_image = u_image;
        title = mtitle;
        image =mimage;
        descr = mdecr;
        price = mprice;
        time = mtime;
        item_type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) { this.user_image = user_image; }

    public String getItem_type() { return item_type; }

    public void setItem_type(String item_type) { this.item_type = item_type; }

    public String getItem_key() { return item_key; }

    public void setItem_key(String item_key) { this.item_key = item_key; }
}
