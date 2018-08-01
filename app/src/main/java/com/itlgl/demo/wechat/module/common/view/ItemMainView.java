package com.itlgl.demo.wechat.module.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 对应R.layout.item_main布局的view类，只提供最基本的ImageView的src设置，TextView的text设置和Badge的数量设置
 * 其余设置可以通过获取这三个控件再进行单独的设置
 */
public class ItemMainView extends RelativeLayout {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.badge_bind_view)
    TextView badgeBindView;
    private Badge badge;
    @BindView(R.id.line)
    View line;

    public ItemMainView(Context context) {
        this(context, null);
    }

    public ItemMainView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.item_main, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemMainView);
        final CharSequence text = ta.getText(R.styleable.ItemMainView_text);
        final Drawable d = ta.getDrawable(R.styleable.ItemMainView_src);
        final String badgeString = ta.getString(R.styleable.ItemMainView_badge);
        final boolean showSplitLine = ta.getBoolean(R.styleable.ItemMainView_splitLine, false);
        final int imageVisibility = ta.getInt(R.styleable.ItemMainView_imageVisibility, View.VISIBLE);
        ta.recycle();

        if(text != null) {
            textView.setText(text);
        }
        if(d != null) {
            imageView.setImageDrawable(d);
        }

        badge = new QBadgeView(getContext()).bindTarget(badgeBindView);
        badge.setBadgeText(badgeString);
        badge.setBadgeBackgroundColor(Color.RED);
        badge.setBadgeGravity(Gravity.CENTER);

        if(showSplitLine) {
            showSplitLine();
        } else {
            hideSplitLine();
        }

        imageView.setVisibility(imageVisibility);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public Badge getBadge() {
        return badge;
    }

    public void showSplitLine() {
        line.setVisibility(View.VISIBLE);
    }

    public void hideSplitLine() {
        line.setVisibility(View.GONE);
    }
}
