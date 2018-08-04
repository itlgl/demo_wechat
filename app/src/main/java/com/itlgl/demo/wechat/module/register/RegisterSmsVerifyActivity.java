package com.itlgl.demo.wechat.module.register;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.common.view.WechatDialog;
import com.itlgl.demo.wechat.module.common.view.WechatProgressDialog;
import com.itlgl.demo.wechat.module.login.PhonePwdLoginActivity;
import com.itlgl.demo.wechat.module.register.bean.RegisterBean;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterSmsVerifyActivity extends RxAppCompatActivity {
    private static final String TAG = "RegisterSmsVerify";
    public static final String EXTRA_REGISTER_BEAN = "extra_register_bean";

    private RegisterBean registerBean;

    @BindView(R.id.input_sms_code_tip)
    TextView tvInputSmsCodeTip;
    @BindView(R.id.sms_verify_code)
    EditText etSmsVerifyCode;
    @BindView(R.id.get_sms_verify_code)
    Button btnGetSmsVerifyCode;
    @BindView(R.id.complete_register)
    Button btnCompleteRegister;

    private Context context = this;
    WechatProgressDialog wechatProgressDialog;
    private boolean hasGetSmsVerifyCodeSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sms_verify);
        ButterKnife.bind(this);

        try {
            registerBean = (RegisterBean) getIntent().getSerializableExtra(EXTRA_REGISTER_BEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(registerBean == null) {
            finish();
        }

        initView();
    }

    private void initView() {
        tvInputSmsCodeTip.setText(getString(R.string.input_sms_code_tip, registerBean.phoneNumber));
        btnGetSmsVerifyCode.setEnabled(false);
        btnCompleteRegister.setEnabled(false);

        etSmsVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0) {
                    btnCompleteRegister.setEnabled(true);
                } else {
                    btnCompleteRegister.setEnabled(false);
                }
            }
        });

        requestSmsCode();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    void backClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.complete_register)
    void completeRegisterClick(View v) {
        verifySmsCode();
    }

    // 请求下发验证码短信
    void requestSmsCode() {
        hasGetSmsVerifyCodeSuccess = false;
        showLoading();
        String formatPhoneNumber = registerBean.regionCode + "-" + registerBean.phoneNumber;
        TLSAccountHelper.getInstance().TLSPwdRegAskCode(formatPhoneNumber, tlsPwdRegListener);
    }
    // 验证验证码是否正确
    void verifySmsCode() {
        showLoading();
        String checkCode = etSmsVerifyCode.getText().toString().trim();
        TLSAccountHelper.getInstance().TLSPwdRegVerifyCode(checkCode, tlsPwdRegListener);
    }
    // 确认注册用户
    void completeRegister() {
        showLoading();
        TLSAccountHelper.getInstance().TLSPwdRegCommit(registerBean.password, tlsPwdRegListener);
    }
    // 注册成功了
    void onRegisterSuccess() {
        // TODO 注册成功以后，跳转到登录界面，然后登录界面直接登录，然后删掉这个dialog
        new WechatDialog.Builder(context)
                .setMessage("注册成功\n(等待登录功能完成)")
                .setPositiveButton(R.string.wechat_dialog_default_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context, PhonePwdLoginActivity.class));
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .show();
    }

    private TLSPwdRegListener tlsPwdRegListener = new TLSPwdRegListener() {

        @Override
        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {
            Log.i(TAG, "OnPwdRegAskCodeSuccess 请求下发短信成功 reaskDuration=" + reaskDuration + ", expireDuration=" + expireDuration);
            hideLoading();
            hasGetSmsVerifyCodeSuccess = true;
            startSmsVerifyCodeButtonCountDown(reaskDuration);
        }

        @Override
        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {
            Log.i(TAG, "OnPwdRegReaskCodeSuccess 重新请求下发短信成功 reaskDuration=" + reaskDuration + ", expireDuration=" + expireDuration);
            hideLoading();
            hasGetSmsVerifyCodeSuccess = true;
            startSmsVerifyCodeButtonCountDown(reaskDuration);
        }

        @Override
        public void OnPwdRegVerifyCodeSuccess() {
            Log.i(TAG, "OnPwdRegVerifyCodeSuccess 验证短信成功");
//            hideLoading();
            completeRegister();
        }

        @Override
        public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
            Log.i(TAG, "OnPwdRegCommitSuccess 密码注册成功 identifier=" + tlsUserInfo.identifier);
//            hideLoading();
            onRegisterSuccess();
        }

        @Override
        public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
            Log.i(TAG, "OnPwdRegFail msg=" + tlsErrInfo.Msg + ",code=" + tlsErrInfo.ErrCode);
            hideLoading();
            showErrorDialog(tlsErrInfo.Msg + "(code:" + tlsErrInfo.ErrCode + ")");
            // TODO 如果点击注册完成失败了，想再次获取验证码又不能，界面就要卡死半分钟，
            // 应该需要判断当前页面的状态，把获取短信验证码的按钮重置回来？可以如果恶意重复发送短信又不好控制
        }

        @Override
        public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
            Log.i(TAG, "OnPwdRegTimeout msg=" + tlsErrInfo.Msg + ",code=" + tlsErrInfo.ErrCode);
            hideLoading();
            showErrorDialog(tlsErrInfo.Msg + "(code:" + tlsErrInfo.ErrCode + ")");
        }
    };

    private Disposable smsCountDownDisposable;

    void startSmsVerifyCodeButtonCountDown(final int reaskDuration) {
        Log.i("test", "counter down reaskDuration=" + reaskDuration);
        if(smsCountDownDisposable != null) {
            smsCountDownDisposable.dispose();
            smsCountDownDisposable = null;
        }

        Observable.interval(1, TimeUnit.SECONDS)
                .take(reaskDuration + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return reaskDuration - aLong;
                    }
                })
                .compose(this.<Long>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        smsCountDownDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        btnGetSmsVerifyCode.setEnabled(false);
                        btnGetSmsVerifyCode.setText(getString(R.string.sms_verify_code_count_down_format, String.valueOf(aLong)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // do nothing
                    }

                    @Override
                    public void onComplete() {
                        btnGetSmsVerifyCode.setEnabled(true);
                        btnGetSmsVerifyCode.setText(R.string.get_sms_verify_code_again);
                    }
                });

    }

    void stopSmsVerifyCodeButtonCountDown() {
        if(smsCountDownDisposable != null) {
            smsCountDownDisposable.dispose();
            smsCountDownDisposable = null;
        }
    }

    public void showLoading() {
        if (wechatProgressDialog == null) {
            wechatProgressDialog = WechatProgressDialog.show(this, R.string.loading, false);
        }
        if(!wechatProgressDialog.isShowing()) {
            wechatProgressDialog.show();
        }
    }

    public void hideLoading() {
        if (wechatProgressDialog != null && wechatProgressDialog.isShowing()) {
            wechatProgressDialog.dismiss();
        }
    }

    void showErrorDialog(String msg) {
        new WechatDialog.Builder(this)
                .setTitle("错误")
                .setMessage(msg)
                .show();
    }

}
