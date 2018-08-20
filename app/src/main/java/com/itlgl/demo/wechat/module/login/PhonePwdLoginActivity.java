package com.itlgl.demo.wechat.module.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.bean.RegionCode;
import com.itlgl.demo.wechat.module.common.SelectRegionCodeActivity;
import com.itlgl.demo.wechat.module.common.view.WechatDialog;
import com.itlgl.demo.wechat.module.common.view.WechatProgressDialog;
import com.itlgl.demo.wechat.module.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class PhonePwdLoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_REGION_CODE = 2;
    private static final int REQUEST_CODE_VERIFY_IMAGE_CODE = 3;

    @BindView(R.id.region_code)
    TextView tvRegionCode;
    @BindView(R.id.phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.clear_phone_number)
    View ivClearPhoneNumber;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.clear_password)
    View ivClearPassword;
    @BindView(R.id.login)
    Button btnLogin;

    private String region, regionCode;
    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_pwd_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 设置默认的国别代码
        region = getString(R.string.default_region);
        regionCode = getString(R.string.default_region_code);
        tvRegionCode.setText(String.format("%s (+%s)", region, regionCode));

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

    private void checkInputs() {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(phoneNumber.length() != 0 && password.length() != 0) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
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

    @SuppressWarnings("unused")
    @OnClick(R.id.login)
    void loginClick(View v) {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, R.string.phone_number_hint, Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return;
        }
        String formatPhoneNumber = regionCode + "-" + phoneNumber;
        String password = etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.password_length_short_error, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }
        showLoading();
        TLSLoginHelper.getInstance().TLSPwdLogin(formatPhoneNumber, password.getBytes(),
                new PwdLoginListener(formatPhoneNumber, password));
    }

    private class PwdLoginListener implements TLSPwdLoginListener {
        private String formatPhoneNumber;
        private String password;

        public PwdLoginListener(String formatPhoneNumber, String password) {
            this.formatPhoneNumber = formatPhoneNumber;
            this.password = password;
        }

        @Override
        public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
            hideLoading();
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }

        @Override
        public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
            hideLoading();
            VerifyImageCodeActivity.callMe(context, formatPhoneNumber, password, bytes, REQUEST_CODE_VERIFY_IMAGE_CODE);
        }

        @Override
        public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
            hideLoading();
            VerifyImageCodeActivity.callMe(context, formatPhoneNumber, password, bytes, REQUEST_CODE_VERIFY_IMAGE_CODE);
        }

        @Override
        public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
            hideLoading();
            showErrorDialog("登录失败\n[" + tlsErrInfo.ErrCode + "-" + tlsErrInfo.Msg + "]");
        }

        @Override
        public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
            hideLoading();
            showErrorDialog("登录失败\n[" + tlsErrInfo.ErrCode + "-" + tlsErrInfo.Msg + "]");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
            case REQUEST_CODE_VERIFY_IMAGE_CODE:
                if(resultCode == RESULT_OK) {
                    // VerifyImageCodeActivity界面在登录成功的时候返回RESULT_OK，这时login界面关闭就可以了
                    finish();
                }
                break;
        }
    }

    private WechatProgressDialog loadingDialog;

    void showLoading() {
        if(loadingDialog == null) {
            loadingDialog = WechatProgressDialog.show(this, R.string.loading);
        } else {
            loadingDialog.show();
        }
    }

    void hideLoading() {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    void showErrorDialog(String msg) {
        new WechatDialog.Builder(this).setMessage(msg).show();
    }
}
