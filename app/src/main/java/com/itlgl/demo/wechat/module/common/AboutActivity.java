package com.itlgl.demo.wechat.module.common;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.common.view.WechatDialog;
import com.itlgl.demo.wechat.module.common.view.WechatProgressDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.about_project)
    void aboutProjectClick(View v) {
        new WechatDialog.Builder(this)
                .setMessage(R.string.project_info)
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.about_me)
    void aboutMeClick(View v) {
        new WechatDialog.Builder(this)
                .setMessage(R.string.me_info)
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    void backClick(View v) {
        finish();
    }

}
