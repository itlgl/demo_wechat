package com.itlgl.demo.wechat.module.login;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LoginUtils {
    public static Bitmap decodeImageCodeBytes(byte[] imgBytes) {
        return BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
    }
}
