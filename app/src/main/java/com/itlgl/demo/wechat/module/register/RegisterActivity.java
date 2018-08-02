package com.itlgl.demo.wechat.module.register;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.module.common.view.WechatDialog;
import com.itlgl.demo.wechat.module.common.view.WechatProgressDialog;
import com.itlgl.demo.wechat.module.register.bean.RegisterBean;
import com.itlgl.demo.wechat.service.areacode.bean.AreaCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.clear_phone_number)
    View clearPhoneNumber;
    @BindView(R.id.clear_psw)
    View clearPassword;
    @BindView(R.id.register)
    Button register;

    WechatProgressDialog wechatProgressDialog;
    AreaCode areaCodeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && phoneNumber.getText().length() != 0) {
                    clearPhoneNumber.setVisibility(View.VISIBLE);
                } else {
                    clearPhoneNumber.setVisibility(View.GONE);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && password.getText().length() != 0) {
                    clearPassword.setVisibility(View.VISIBLE);
                } else {
                    clearPassword.setVisibility(View.GONE);
                }
            }
        });
        nickname.addTextChangedListener(inputsTextWacher);
        phoneNumber.addTextChangedListener(inputsTextWacher);
        password.addTextChangedListener(inputsTextWacher);

        // TODO 区域变量国际化，不能直接写在代码里面；在string文件中用array表示所有的区域码
        setAreaCode(new AreaCode(86, "中国", "ZHONGGUO"));
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
        String nicknameStr = nickname.getText().toString();
        String areaCodeStr = areaCode.getText().toString();
        String phoneNumberStr = phoneNumber.getText().toString();
        String passwordStr = password.getText().toString();
        if (TextUtils.isEmpty(nicknameStr) || TextUtils.isEmpty(areaCodeStr)
                || TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(passwordStr)) {
            register.setEnabled(false);
        } else {
            register.setEnabled(true);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.area_code)
    void areaCodeClick(View v) {
        new WechatDialog.Builder(this)
                .setMessage("正在开发中...")
                .show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.clear_phone_number)
    void clearPhoneNumberClick(View v) {
        phoneNumber.setText("");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.clear_psw)
    void clearPasswordClick(View v) {
        password.setText("");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.register)
    void registerClick(View v) {

    }

    void setAreaCode(AreaCode areaCodeBean) {
        this.areaCodeBean = areaCodeBean;
        areaCode.setText(areaCodeBean.area + "（+" + areaCodeBean.areaCode + "）");
    }

    // --- view 接口实现方法 ---
    @Override
    public RegisterBean getRegisterInfo() {
        return new RegisterBean()
                .setNickname(nickname.getText().toString().trim())
                .setAvatar(null)
                .setAreaCode("86")
                .setPhoneNumber(phoneNumber.getText().toString().trim())
                .setPassword(password.getText().toString().trim());
    }

    @Override
    public void showLoading() {
        if (wechatProgressDialog == null) {
            wechatProgressDialog = WechatProgressDialog.show(this, R.string.register_loading_text);
        } else {
            wechatProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (wechatProgressDialog != null && wechatProgressDialog.isShowing()) {
            wechatProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorDialog(String errorMsg) {
        new WechatDialog.Builder(this)
                .setTitle(R.string.register_error)
                .setMessage(errorMsg)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
