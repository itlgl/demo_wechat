package com.itlgl.demo.wechat.module.register;

import com.itlgl.demo.wechat.module.register.bean.RegisterBean;

public interface RegisterView {
    RegisterBean getRegisterInfo();
    void showLoading();
    void hideLoading();
    void showErrorDialog(String errorMsg);
}
