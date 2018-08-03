package com.itlgl.demo.wechat.module.common.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;

public class WechatDialog extends Dialog {
    public static final int COLOR_GRAY = Color.parseColor("#6E6E6E");
    public static final int COLOR_BLUE = Color.parseColor("#1AAD19");
    public static final int COLOR_RED = Color.parseColor("#FF3E3E");

    public WechatDialog(@NonNull Context context) {
        super(context, R.style.WechatDialogStyle);
    }

    public static class Builder {
        private Context context;
        private WechatDialog wechatDialog;
        private CharSequence title;
        private CharSequence message;
        private CharSequence positiveButtonText;
        private CharSequence negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private boolean cancelable = true;
        private int positiveTextColor;
        private int negativeTextColor;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int titleId) {
            this.title = context.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(int messageId) {
            this.message = context.getText(messageId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(int textId, OnClickListener listener) {
            this.positiveButtonText = context.getText(textId);
            this.positiveTextColor = COLOR_BLUE;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            this.positiveButtonText = text;
            this.positiveTextColor = COLOR_BLUE;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, OnClickListener listener) {
            this.negativeButtonText = context.getText(textId);
            this.negativeTextColor = COLOR_GRAY;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            this.negativeButtonText = text;
            this.negativeTextColor = COLOR_GRAY;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setPositiveTextColor(int color) {
            this.positiveTextColor = color;
            return this;
        }

        public Builder setNegativeTextColor(int color) {
            this.negativeTextColor = color;
            return this;
        }

        public Builder setDialogButtonTypeError() {
            this.positiveTextColor = COLOR_RED;
            this.negativeTextColor = COLOR_GRAY;
            return this;
        }

        public Builder setDialogButtonTypeTip() {
            this.positiveTextColor = COLOR_BLUE;
            this.negativeTextColor = COLOR_GRAY;
            return this;
        }

        public WechatDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_wechat, null);
            wechatDialog = new WechatDialog(context);
            wechatDialog.setCancelable(cancelable);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.title);
            TextView tvMessage = (TextView) dialogView.findViewById(R.id.message);
            TextView btnCancel = dialogView.findViewById(R.id.cancel_btn);
            TextView btnConfirm = dialogView.findViewById(R.id.confirm_btn);

            // 设置标题
            if(TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(title);
            }
            // 设置内容区域
            if (contentView != null) {
                // if no message set add the contentView to the dialog body
                LinearLayout rl = (LinearLayout) dialogView
                        .findViewById(R.id.message_layout);
                rl.removeAllViews();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                rl.addView(contentView, params);
            } else {
                tvMessage.setText(message);
                // 为了让超链接文字可点击
                tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
            }

            // 设置按钮区域
            if(positiveButtonText == null && negativeButtonText == null) {
                setPositiveButton(R.string.wechat_dialog_default_ok, null);
                btnCancel.setVisibility(View.GONE);
            } else if(positiveButtonText != null && negativeButtonText == null) {
                btnCancel.setVisibility(View.GONE);
            } else if(positiveButtonText == null && negativeButtonText != null) {
                btnConfirm.setVisibility(View.GONE);
            }
            if (positiveButtonText != null) {
                btnConfirm.setText(positiveButtonText);
                btnConfirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (positiveButtonClickListener != null) {
                            positiveButtonClickListener.onClick(wechatDialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                        wechatDialog.dismiss();
                    }
                });
            }
            if (negativeButtonText != null) {
                btnCancel.setText(negativeButtonText);
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (negativeButtonClickListener != null) {
                            negativeButtonClickListener.onClick(wechatDialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                        wechatDialog.dismiss();
                    }
                });
            }

            // 调整一下dialog的高度，如果高度充满屏幕不好看
            // 计算一下Dialog的高度,如果超过屏幕的4/5，则最大高度限制在4/5
            // 1.计算dialog的高度
            // TODO 测试发现的问题：如果放入一大串没有换行的文本到message区域，会导致测量出来的高度偏小，从而导致实际显示出来dialog充满了整个屏幕
            dialogView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int dialogHeight = dialogView.getMeasuredHeight();
            // 2.得到屏幕高度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            int maxHeight = (int) (metrics.heightPixels * 0.8);
            ViewGroup.LayoutParams dialogParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // 3.如果高度超限，限制dialog的高度
            if(dialogHeight >= maxHeight) {
                dialogParams.height = maxHeight;
            }
            wechatDialog.setContentView(dialogView, dialogParams);

            return wechatDialog;
        }

        public WechatDialog show() {
            wechatDialog = create();
            wechatDialog.show();
            return wechatDialog;
        }
    }
}
