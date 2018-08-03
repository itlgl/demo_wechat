package com.itlgl.demo.wechat.module.register.bean;

import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;

public class RegisterBean implements Serializable {
    public String nickname;
    public File avatarFilePath;
    /**
     * ex:86
     */
    public String regionCode;
    public String phoneNumber;
    public String password;

    public RegisterBean() {}

    public boolean isAllInputsFilled() {
        return !TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(regionCode)
                && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password);
    }

    public RegisterBean setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public RegisterBean setAvatarFilePath(File avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
        return this;
    }

    public RegisterBean setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
