package com.coresoft.electricalsolutions.Modal;

public class Users {
    String email,pass,mobile,name,uid,profile_pic;
    String createdAt;

    public Users() {
    }

    public Users(String email, String pass, String mobile, String name, String uid, String profile_pic, String createdAt) {
        this.email = email;
        this.pass = pass;
        this.mobile = mobile;
        this.name = name;
        this.uid = uid;
        this.profile_pic = profile_pic;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
