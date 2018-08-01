package com.itlgl.demo.wechat.module.register;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class RegisterActivity extends AppCompatActivity {

    // TLSHelper tlsHelper;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_register);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);

        Button btn = null;

        btn = new Button(this);
        btn.setText("初始化TLS SDK");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tlsHelper = TLSHelper.getInstance().init(context, IMConsts.SDK_APP_ID);
            }
        });
        ll.addView(btn);

        btn = new Button(this);
        btn.setText("请求发送验证码");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tlsHelper.TLSPwdRegAskCode("86-15631156915", tlsPwdRegListener);
            }
        });
        ll.addView(btn);
    }

//    private TLSPwdRegListener tlsPwdRegListener = new TLSPwdRegListener() {
//        @Override
//        public void OnPwdRegAskCodeSuccess(int i, int i1) {
//            Log.i("test", "OnPwdRegAskCodeSuccess");
//            Log.i("test", "thread=" + Thread.currentThread().getName());
//        }
//
//        @Override
//        public void OnPwdRegReaskCodeSuccess(int i, int i1) {
//            Log.i("test", "OnPwdRegReaskCodeSuccess");
//        }
//
//        @Override
//        public void OnPwdRegVerifyCodeSuccess() {
//            Log.i("test", "OnPwdRegVerifyCodeSuccess");
//        }
//
//        @Override
//        public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
//            Log.i("test", "OnPwdRegCommitSuccess");
//        }
//
//        @Override
//        public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
//            Log.i("test", "OnPwdRegFail");
//        }
//
//        @Override
//        public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
//            Log.i("test", "OnPwdRegTimeout");
//        }
//    };
}
