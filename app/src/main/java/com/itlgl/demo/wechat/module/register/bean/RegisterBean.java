package com.itlgl.demo.wechat.module.register.bean;

import android.net.Uri;

public class RegisterBean {
    public String nickname;
    public Uri avatar;
    public String areaCode;
    public String phoneNumber;
    public String password;

    public RegisterBean() {}

    public RegisterBean setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public RegisterBean setAvatar(Uri avatar) {
        this.avatar = avatar;
        return this;
    }

    public RegisterBean setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public RegisterBean setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RegisterBean setPassword(String password) {
        this.password = password;
        return this;
    }
}
