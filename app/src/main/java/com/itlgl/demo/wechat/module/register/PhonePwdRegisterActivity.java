package com.itlgl.demo.wechat.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.bean.RegionCode;
import com.itlgl.demo.wechat.module.common.SelectRegionCodeActivity;
import com.itlgl.demo.wechat.module.common.view.WechatDialog;
import com.itlgl.demo.wechat.module.common.view.WechatProgressDialog;
import com.itlgl.demo.wechat.module.register.bean.RegisterBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhonePwdRegisterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SMS_VERIFY = 1;
    private static final int REQUEST_CODE_SELECT_REGION_CODE = 2;

    @BindView(R.id.nickname)
    EditText etNickname;
    @BindView(R.id.avatar)
    ImageView ivAvatar;
    @BindView(R.id.region_code)
    TextView tvRegionCode;
    @BindView(R.id.phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.clear_phone_number)
    View ivClearPhoneNumber;
//    @BindView(R.id.sms_verify_code)
//    EditText etSmsVerifyCode;
//    @BindView(R.id.get_sms_verify_code)
//    Button btnGetSmsVerifyCode;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.clear_password)
    View ivClearPassword;
    @BindView(R.id.register)
    Button btnRegister;

    WechatProgressDialog wechatProgressDialog;

    private String region, regionCode;
//    private boolean hasGetSmsVerifyCodeSuccess = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pwd_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        region = getString(R.string.default_region);
        regionCode = getString(R.string.default_region_code);
        tvRegionCode.setText(String.format("%s (+%s)", region, regionCode));

//        btnGetSmsVerifyCode.setEnabled(false);
        btnRegister.setEnabled(false);

        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && etPhoneNumber.getText().length() != 0) {
                    ivClearPhoneNumber.setVisibility(View.VISIBLE);
                } else {
                    ivClearPhoneNumber.setVisibility(View.GONE);
                }
            }
        });
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etPhoneNumber.hasFocus() && s.length() != 0) {
                    ivClearPhoneNumber.setVisibility(View.VISIBLE);
                } else {
                    ivClearPhoneNumber.setVisibility(View.GONE);
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && etPassword.getText().length() != 0) {
                    ivClearPassword.setVisibility(View.VISIBLE);
                } else {
                    ivClearPassword.setVisibility(View.GONE);
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etPassword.hasFocus() && s.length() != 0) {
                    ivClearPassword.setVisibility(View.VISIBLE);
                } else {
                    ivClearPassword.setVisibility(View.GONE);
                }
            }
        });
//        etPhoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.length() != 0) {
//                    btnGetSmsVerifyCode.setEnabled(true);
//                } else {
//                    btnGetSmsVerifyCode.setEnabled(false);
//                }
//            }
//        });

        // 监听所有输入的变化，当所有输入全不为空时，才允许进行注册操作
        etNickname.addTextChangedListener(inputsTextWacher);
        tvRegionCode.addTextChangedListener(inputsTextWacher);
        etPhoneNumber.addTextChangedListener(inputsTextWacher);
        etPassword.addTextChangedListener(inputsTextWacher);
    }

    private TextWatcher inputsTextWacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkInputs();
        }
    };

    // 检查所有的输入框是否都有值，如果都输入了值，那么就把注册按钮置为enable状态；反之为disable状态
    private void checkInputs() {
        RegisterBean registerBean = getRegisterInfo();
        if(registerBean.isAllInputsFilled()) {
            btnRegister.setEnabled(true);
        } else {
            btnRegister.setEnabled(false);
        }
    }

    public RegisterBean getRegisterInfo() {
        return new RegisterBean()
                .setNickname(etNickname.getText().toString().trim())
                .setRegionCode(regionCode)
                .setPhoneNumber(etPhoneNumber.getText().toString().trim())
//                .setSmsVerifyCode(etSmsVerifyCode.getText().toString().trim())
                .setPassword(etPassword.getText().toString().trim());
    }

    public void showLoading() {
        if (wechatProgressDialog == null) {
            wechatProgressDialog = WechatProgressDialog.show(this, R.string.loading);
        } else {
            wechatProgressDialog.show();
        }
    }

    public void hideLoading() {
        if (wechatProgressDialog != null && wechatProgressDialog.isShowing()) {
            wechatProgressDialog.dismiss();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    void backClick(View v) {
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.layout_region_code)
    void areaCodeClick(View v) {
        startActivityForResult(new Intent(this, SelectRegionCodeActivity.class), REQUEST_CODE_SELECT_REGION_CODE);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.clear_phone_number)
    void clearPhoneNumberClick(View v) {
        etPhoneNumber.setText("");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.clear_password)
    void clearPasswordClick(View v) {
        etPassword.setText("");
    }

//    @SuppressWarnings("unused")
//    @OnClick(R.id.get_sms_verify_code)
//    void getSmsVerifyCodeClick(View v) {
//        showLoading();
//        btnGetSmsVerifyCode.setEnabled(false);
//        RegisterBean registerBean = getRegisterInfo();
//        String formatPhoneNumber = registerBean.regionCode.substring(1) + "-" + registerBean.phoneNumber;
//        TLSAccountHelper.getInstance().TLSPwdRegAskCode(formatPhoneNumber, tlsPwdRegListener);
//    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register)
    void registerClick(View v) {
        RegisterBean registerBean = getRegisterInfo();
        // TODO 验证手机号码是否合法
        if(registerBean.password.length() < 6) {
            showErrorDialog(getString(R.string.password_length_short_error));
            etPassword.requestFocus();
            return;
        }

        Intent intent = new Intent(this, RegisterSmsVerifyActivity.class);
        intent.putExtra(RegisterSmsVerifyActivity.EXTRA_REGISTER_BEAN, registerBean);
        startActivityForResult(intent, REQUEST_CODE_SMS_VERIFY);
        // 如果获取验证码什么的失败了，也要回到这个界面
        // finish();
    }

    void showErrorDialog(String msg) {
        new WechatDialog.Builder(this)
                .setTitle("输入错误")
                .setMessage(msg)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SMS_VERIFY:
                if(resultCode == RESULT_OK) {
                    // 如果返回RESULT_OK，表示注册成功了，这时就要关闭注册页面
                    finish();
                }
                break;
            case REQUEST_CODE_SELECT_REGION_CODE:
                if(resultCode == RESULT_OK) {
                    try {
                        RegionCode rc = (RegionCode) data.getSerializableExtra(SelectRegionCodeActivity.EXTRA_REGION_CODE);
                        region = rc.getName();
                        regionCode = String.valueOf(rc.getCode());
                        tvRegionCode.setText(String.format("%s (+%s)", region, regionCode));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    //    void startSmsVerifyCodeCountDown(final int reaskDuration) {
//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(reaskDuration + 1)
//                .map(new Function<Long, Long>() {
//                    @Override
//                    public Long apply(Long aLong) throws Exception {
//                        return reaskDuration - aLong;
//                    }
//                })
//                .compose(this.<Long>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        btnGetSmsVerifyCode.setEnabled(false);
//                        btnGetSmsVerifyCode.setText(getString(R.string.sms_verify_code_count_down_format, String.valueOf(aLong)));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        btnGetSmsVerifyCode.setEnabled(true);
//                        btnGetSmsVerifyCode.setText(R.string.get_sms_verify_code_again);
//                    }
//                });
//
//    }

//    private TLSPwdRegListener tlsPwdRegListener = new TLSPwdRegListener() {
//        @Override
//        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {
//            hasGetSmsVerifyCodeSuccess = true;
//            hideLoading();
//            Toast.makeText(context, "请求下发短信成功,验证码" + expireDuration / 60 + "分钟内有效", Toast.LENGTH_SHORT).show();
//
//            // 在获取验证码按钮上显示重新获取验证码的时间间隔
//            startSmsVerifyCodeCountDown(reaskDuration);
//        }
//
//        @Override
//        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {
//            hasGetSmsVerifyCodeSuccess = true;
//            hideLoading();
//            Toast.makeText(context, "重新请求下发短信成功,验证码" + expireDuration / 60 + "分钟内有效", Toast.LENGTH_SHORT).show();
//
//            // 在获取验证码按钮上显示重新获取验证码的时间间隔
//            startSmsVerifyCodeCountDown(reaskDuration);
//        }
//
//        @Override
//        public void OnPwdRegVerifyCodeSuccess() {
////            Util.showToast(context, "注册验证通过，准备获取号码");
////            Intent intent = new Intent(context, PhonePwdLoginActivity.class);
////            intent.putExtra(Constants.EXTRA_PHONEPWD_REG_RST, Constants.PHONEPWD_REGISTER);
////            intent.putExtra(Constants.COUNTRY_CODE, txt_countryCode.getText().toString());
////            intent.putExtra(Constants.PHONE_NUMBER, txt_phoneNumber.getText().toString());
////            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
////            context.startActivity(intent);
////            ((Activity)context).finish();
//        }
//
//        @Override
//        public void OnPwdRegCommitSuccess(TLSUserInfo userInfo) {}
//
//        @Override
//        public void OnPwdRegFail(TLSErrInfo errInfo) {
//            hideLoading();
//            showErrorDialog(errInfo.Msg);
//            checkInputs();
//        }
//
//        @Override
//        public void OnPwdRegTimeout(TLSErrInfo errInfo) {
//            hideLoading();
//            showErrorDialog(errInfo.Msg);
//            checkInputs();
//        }
//    };
}
