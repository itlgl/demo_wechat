package com.itlgl.demo.wechat.module.main.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.itlgl.demo.wechat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ItemRecentlyChatView extends FrameLayout {
    @BindView(R.id.avatar)
    ImageView ivAvatar;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.message)
    TextView tvMessage;
    @BindView(R.id.datetime)
    TextView tvDatetime;
    @BindView(R.id.tv_badge_to_bind)
    TextView tvBadgeToBind;
    private Badge badge;

    public ItemRecentlyChatView(Context context) {
        this(context, null);
    }

    public ItemRecentlyChatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemRecentlyChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.item_recently_chat, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemRecentlyChatView);
        final Drawable d = ta.getDrawable(R.styleable.ItemRecentlyChatView_src);
        final CharSequence title = ta.getText(R.styleable.ItemRecentlyChatView_title);
        final CharSequence message = ta.getText(R.styleable.ItemRecentlyChatView_message);
        final CharSequence datetime = ta.getText(R.styleable.ItemRecentlyChatView_datetime);
        final String badgeString = ta.getString(R.styleable.ItemImageTextView_badge);
        ta.recycle();

        if(d != null) {
            ivAvatar.setImageDrawable(d);
        }
        if(title != null) {
            tvTitle.setText(title);
        }
        if(message != null) {
            tvMessage.setText(message);
        }
        if(datetime != null) {
            tvDatetime.setText(datetime);
        }

        badge = new QBadgeView(getContext()).bindTarget(tvBadgeToBind);
        badge.setBadgeBackgroundColor(Color.RED);
        badge.setBadgeGravity(Gravity.END | Gravity.TOP);
        if(badgeString != null) {
            badge.setBadgeText(badgeString);
        }
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setMessage(CharSequence message) {
        tvMessage.setText(message);
    }

    public void setBadgeString(String badgeString) {
        badge.setBadgeText(badgeString);
    }

    public void setDatetime(String datetime) {
        tvDatetime.setText(datetime);
    }

    public void setAvatar(Bitmap avatar) {
        ivAvatar.setImageBitmap(avatar);
    }
}
