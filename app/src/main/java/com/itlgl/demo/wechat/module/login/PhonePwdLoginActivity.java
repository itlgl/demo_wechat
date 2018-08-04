package com.itlgl.demo.wechat.module.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;
import com.itlgl.demo.wechat.bean.RegionCode;
import com.itlgl.demo.wechat.module.common.SelectRegionCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhonePwdLoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_REGION_CODE = 2;

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

    }

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
        }
    }
}
