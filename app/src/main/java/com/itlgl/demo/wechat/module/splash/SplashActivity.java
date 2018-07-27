package com.itlgl.demo.wechat.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.main.MainActivity;
import com.itlgl.demo.wechat.utils.BarUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends RxAppCompatActivity {

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

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        finishTask();
                    }
                });
    }

    private void finishTask() {

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.select_language)
    void selectLanguageClick(View v) {
        Toast.makeText(this, "选择语言", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.login)
    void loginClick(View v) {
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register)
    void registerClick(View v) {
        Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
    }

    // test
    @SuppressWarnings("unused")
    @OnClick(R.id.background)
    void backgroundClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
