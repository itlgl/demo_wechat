package com.itlgl.demo.wechat.module.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.itlgl.tencent.qcloud.Constants;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSLoginHelper;

public class WechatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(MsfSdkUtils.isMainProcess(this)) {
            initTimManager();
        }
    }

    void initTimManager() {
        // 初始化TIMManager
        TIMSdkConfig config = new TIMSdkConfig(Constants.SDK_APPID);
        config.enableLogPrint(true).setLogLevel(TIMLogLevel.INFO);
        TIMManager.getInstance().init(this, config);
        // 初始化Login的TLS托管
        String appVersion = getAppVersion();
        TLSLoginHelper.getInstance().init(this, Constants.SDK_APPID, Constants.ACCOUNT_TYPE, appVersion);
        TLSAccountHelper.getInstance().init(this, Constants.SDK_APPID, Constants.ACCOUNT_TYPE, appVersion);
    }

    String getAppVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }
}
