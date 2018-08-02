package com.itlgl.demo.wechat.module.common.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 微信样式灰色的progress dialog
 */
public class WechatProgressDialog extends Dialog {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.text)
    TextView textView;

    public WechatProgressDialog(@NonNull Context context) {
        super(context, R.style.WechatProgressDialogStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.wechat_progress_dialog, null);
        ButterKnife.bind(this, dialogView);
        ViewGroup.LayoutParams dialogParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialogView, dialogParams);
    }

    public static WechatProgressDialog show(Context context, CharSequence message) {
        return show(context, message, true, null);
    }

    public static WechatProgressDialog show(Context context, int messageId) {
        return show(context, messageId, true, null);
    }

    public static WechatProgressDialog show(Context context, CharSequence message, boolean cancelable) {
        return show(context, message, cancelable, null);
    }

    public static WechatProgressDialog show(Context context, int messageId, boolean cancelable) {
        return show(context, messageId, cancelable, null);
    }

    public static WechatProgressDialog show(Context context, int messageId,
            boolean cancelable, OnCancelListener cancelListener) {
        return show(context, context.getString(messageId), cancelable, cancelListener);
    }

    public static WechatProgressDialog show(Context context, CharSequence message,
            boolean cancelable, OnCancelListener cancelListener) {
        WechatProgressDialog progress = new WechatProgressDialog(context);
        progress.setMessage(message);
        progress.setCancelable(cancelable);
        progress.setOnCancelListener(cancelListener);
        progress.show();
        return progress;
    }

    public void setMessage(CharSequence message) {
        textView.setText(message);
    }

    public CharSequence getMessage() {
        return textView.getText();
    }

}
