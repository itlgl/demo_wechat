package com.itlgl.demo.wechat.module.main.adapter;

import android.graphics.Bitmap;

public class RecentlyChatBean {
    public Bitmap avatar;
    public CharSequence title;
    public CharSequence message;
    public CharSequence datetime;

    public RecentlyChatBean() {}

    public RecentlyChatBean(Bitmap avatar, CharSequence title, CharSequence message, CharSequence datetime) {
        this.avatar = avatar;
        this.title = title;
        this.message = message;
        this.datetime = datetime;
    }
}
