package com.itlgl.demo.wechat.module.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itlgl.demo.wechat.R;
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

public class VerifyImageCodeActivity extends AppCompatActivity {

    public final static String EXTRA_IMG_CHECK_CODE = "EXTRA_IMG_CHECK_CODE";
    public final static String EXTRA_FORMAT_PHONE_NUMBER = "EXTRA_FORMAT_PHONE_NUMBER";
    public final static String EXTRA_PASSWORD = "EXTRA_PASSWORD";

    @BindView(R.id.iv_image_code)
    ImageView ivImageCode;
    @BindView(R.id.et_image_code)
    EditText etImageCode;
    @BindView(R.id.clear_image_code)
    View vClearImageCode;
    @BindView(R.id.login)
    Button btnLogin;

    private Context context = this;

    private String formatPhoneNumber;
    private String password;

    public static void callMe(Activity activity, String formatPhoneNumber, String password,
                              byte[] imageCodeBytes, int requestCode) {
        Intent intent = new Intent(activity, VerifyImageCodeActivity.class);
        intent.putExtra(EXTRA_IMG_CHECK_CODE, imageCodeBytes);
        intent.putExtra(EXTRA_FORMAT_PHONE_NUMBER, formatPhoneNumber);
        intent.putExtra(EXTRA_PASSWORD, password);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_image_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        formatPhoneNumber = getIntent().getStringExtra(EXTRA_FORMAT_PHONE_NUMBER);
        password = getIntent().getStringExtra(EXTRA_PASSWORD);
        if(TextUtils.isEmpty(formatPhoneNumber) || TextUtils.isEmpty(password)) {
            finish();
            return;
        }

        etImageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0) {
                    vClearImageCode.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(true);
                } else {
                    vClearImageCode.setVisibility(View.GONE);
                    btnLogin.setEnabled(false);
                }
            }
        });

        byte[] imageCodeBytes = getIntent().getByteArrayExtra(EXTRA_IMG_CHECK_CODE);
        if(imageCodeBytes != null && imageCodeBytes.length != 0) {
            Bitmap bp = LoginUtils.decodeImageCodeBytes(imageCodeBytes);
            ivImageCode.setImageBitmap(bp);
        } else {
            refreshImageCode();
        }
    }

    private TLSPwdLoginListener pwdLoginListener = new TLSPwdLoginListener() {

        @Override
        public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
            hideLoading();
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }

        @Override
        public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
            hideLoading();
            Bitmap bp = LoginUtils.decodeImageCodeBytes(bytes);
            ivImageCode.setImageBitmap(bp);
        }

        @Override
        public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
            hideLoading();
            Bitmap bp = LoginUtils.decodeImageCodeBytes(bytes);
            ivImageCode.setImageBitmap(bp);
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

    @SuppressWarnings("unused")
    @OnClick(R.id.iv_image_code)
    void ivImageCodeClick(View v) {
        refreshImageCode();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.back)
    void backClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.login)
    void loginClick(View v) {
        String imageCode = etImageCode.getText().toString().trim();
        if(TextUtils.isEmpty(imageCode)) {
            etImageCode.requestFocus();
            Toast.makeText(this, R.string.image_code_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        TLSLoginHelper.getInstance().TLSPwdLoginVerifyImgcode(imageCode, pwdLoginListener);
    }

    void refreshImageCode() {
        showLoading();
        ivImageCode.setImageBitmap(null);
        TLSLoginHelper.getInstance().TLSPwdLoginReaskImgcode(pwdLoginListener);
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
