package com.itlgl.demo.wechat.module.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.login.PhonePwdLoginActivity;
import com.itlgl.demo.wechat.module.main.MainActivity;
import com.itlgl.demo.wechat.module.register.PhonePwdRegisterActivity;
import com.itlgl.demo.wechat.utils.BarUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSUserInfo;

public class SplashActivity extends RxAppCompatActivity {

    private final int REQUEST_PHONE_PERMISSIONS = 0;

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        BarUtils.setNoBar(this);
        login.setVisibility(View.GONE);
        register.setVisibility(View.GONE);

        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ((checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionsList.size() == 0){
                init();
            }else{
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        } else {
            init();
        }
    }

    private void init() {
        TLSUserInfo userInfo = TLSLoginHelper.getInstance().getLastUserInfo();
        if(userInfo != null && !TextUtils.isEmpty(userInfo.identifier)) {
            Observable.timer(2000, TimeUnit.MILLISECONDS)
                    .compose(this.<Long>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        } else {
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    Toast.makeText(this, getString(R.string.need_permission),Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.select_language)
    void selectLanguageClick(View v) {
        Toast.makeText(this, "选择语言", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.login)
    void loginClick(View v) {
        startActivity(new Intent(this, PhonePwdLoginActivity.class));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register)
    void registerClick(View v) {
        startActivity(new Intent(this, PhonePwdRegisterActivity.class));
    }

    // test
    @SuppressWarnings("unused")
    @OnClick(R.id.background)
    void backgroundClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
