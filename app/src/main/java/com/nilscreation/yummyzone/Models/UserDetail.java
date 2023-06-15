package com.nilscreation.yummyzone.Models;

public class UserDetail {

    String Userid, Username, Mobile, Email;

    public UserDetail(String userid, String username, String mobile, String email) {
        Userid = userid;
        Username = username;
        Mobile = mobile;
        Email = email;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
