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
    private Badge badge;

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
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        inflater.inflate(R.layout.item_main, this, true);
        inflate(getContext(), R.layout.item_main, this);
        ButterKnife.bind(this);

        Log.i("test", "ItemMainView imageView=" + imageView);
        Log.i("test", "ItemMainView textView=" + textView);
        Log.i("test", "ItemMainView badge=" + badge);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemMainView);
        final CharSequence text = ta.getText(R.styleable.ItemMainView_text);
        final Drawable d = ta.getDrawable(R.styleable.ItemMainView_src);
        final int badgeCount = ta.getInteger(R.styleable.ItemMainView_badgeCount, 0);
        ta.recycle();

        if(text != null) {
            textView.setText(text);
        }
        if(d != null) {
            imageView.setImageDrawable(d);
        }

        badge = new QBadgeView(getContext()).bindTarget(textView);
        badge.setBadgeNumber(badgeCount);
        badge.setBadgeBackgroundColor(Color.RED);
        badge.setBadgeGravity(Gravity.CENTER | Gravity.END);
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
}
