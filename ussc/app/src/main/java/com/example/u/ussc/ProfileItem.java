package com.example.u.ussc;

/**
 * Created by Lundy on 2/18/2018.
 */

public class ProfileItem {
    private String muserId;
    private String muserEmail;
    private String name;
    private String major;
    private String images;
    private String signUpDate;

    public  ProfileItem() {}

    public  ProfileItem(String _muserid,  String _muserEmail, String _name, String _muserDate, String muri) {
        muserId = _muserid;
        muserEmail = _muserEmail;
        name = _name;
        signUpDate = _muserDate;
        images = muri;

    }

    public  ProfileItem(String _muserid,  String _muserEmail, String _name) {
        muserId = _muserid;
        muserEmail = _muserEmail;
        name = _name;
    }

    public void setMUserId(String muserid) {
        this.muserId = muserid;
    }

    public void setMUserEmail(String museremail) {
        this.muserEmail = museremail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMUserId() { return muserId;}

    public String getMUserEmail() { return muserEmail;}

    public String getName() { return name;}

    public String getMajor() { return major;}

    public String getImages() { return images;}
}
