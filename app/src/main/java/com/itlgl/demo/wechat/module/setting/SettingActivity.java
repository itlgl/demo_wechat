package com.itlgl.demo.wechat.module.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.common.AboutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    void backClick(View v) {
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.about_wechat)
    void aboutWechatClick(View v) {
        startActivity(new Intent(this, AboutActivity.class));
    }
}
