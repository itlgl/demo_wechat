package com.itlgl.demo.wechat.module.main.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.util.AttributeSet;

import com.itlgl.demo.wechat.R;

/**
 * Created by Droidroid on 2016/5/10.
 */
public class WeChatRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private Paint mTextPaint;
    private Paint mNotificationPaint;

    private int iconWidth;
    private int iconPadding;
    private int iconHeight;

    private Drawable mFocusDrawable;
    private Drawable mDeFocusDrawable;
    private Drawable mMiddleDrawable;

    private Rect mDrawableRect;

    private int mAlpha;
    private int mColor;
    private float mFontHeight;
    private float mTextWidth;

    /**
     * 这个变量来记录当前按钮需要显示的notification的个数
     * 如果为<0，表示需要显示无数量的小红点
     * 如果为0，表示不显示
     * 如果>0，显示数量的小红点，在>99以后显示99+的字样
     */
    private int mNotificationCount = 0;
    private float mDotRadius = 0;

    public WeChatRadioButton(Context context) {
        this(context, null);
    }

    public WeChatRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mNotificationPaint = new Paint();
        mNotificationPaint.setColor(Color.RED);
        mNotificationPaint.setAntiAlias(true);

        mDrawableRect = new Rect();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeChatRadioButton);
        mFocusDrawable = ta.getDrawable(R.styleable.WeChatRadioButton_focus_icon);
        mDeFocusDrawable = ta.getDrawable(R.styleable.WeChatRadioButton_defocus_icon);
        mColor = ta.getColor(R.styleable.WeChatRadioButton_focus_color, Color.BLUE);
        ta.recycle();

        setButtonDrawable(null);
        // 这段代码的意义在于获取控件的大小，然后根据控件的大小来绘制接下来的图标
        if (mDeFocusDrawable != null) {
            setCompoundDrawablesWithIntrinsicBounds(null, mDeFocusDrawable, null, null);
            mDeFocusDrawable = getCompoundDrawables()[1];
        }

        if (mFocusDrawable == null || mDeFocusDrawable == null) {
            throw new RuntimeException("'focus_icon' and 'defocus_icon' attributes should be defined");
        }
    }

    /**
     * 这个变量来记录当前按钮需要显示的notification的个数
     * 如果为<0，表示需要显示无数量的小红点
     * 如果为0，表示不显示
     * 如果>0，显示数量的小红点，在>99以后显示99+的字样
     *
     * @param count notification count
     */
    public void updateNotification(int count) {
        this.mNotificationCount = count;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // iconWidth = mDeFocusDrawable.getIntrinsicWidth();
        // iconHeight = mDeFocusDrawable.getIntrinsicHeight();
        iconHeight = (int) (h / 2);
        iconWidth = (int) (iconHeight * 1.0f * mFocusDrawable.getIntrinsicWidth() / mFocusDrawable.getIntrinsicHeight());

        mDotRadius = iconHeight / 6.0f;

        mDeFocusDrawable.setBounds(0, 0, iconWidth, iconHeight);
        mFocusDrawable.setBounds(0, 0, iconWidth, iconHeight);

        iconPadding = getCompoundDrawablePadding();

        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        mFontHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
        mTextWidth = StaticLayout.getDesiredWidth(getText(), getPaint());

        mMiddleDrawable = DrawableUtil.tintDrawable(mDeFocusDrawable, mColor);

        if (isChecked()) {
            mAlpha = 255;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 提前设置一下drawable的rect，免得几个drawable重复计算
        mDrawableRect.set((getWidth() - iconWidth) / 2, getPaddingTop(), (getWidth() - iconWidth) / 2 + iconWidth, getPaddingTop() + iconHeight);
        drawDeFocusIcon(canvas);
        drawMiddleIcon(canvas);
        drawFocusIcon(canvas);
        drawDeFocusText(canvas);
        drawFocusText(canvas);
        drawNotification(canvas);
    }

    private void drawDeFocusIcon(Canvas canvas) {
        int alpha = 255 - mAlpha * 2;
        if(alpha < 0) {
            alpha = 0;
        }
        mDeFocusDrawable.setBounds(mDrawableRect);
        mDeFocusDrawable.setAlpha(alpha);
        mDeFocusDrawable.draw(canvas);
    }

    private void drawMiddleIcon(Canvas canvas) {
        int alpha;
        float alphaF = mAlpha;
        if(alphaF < 255 / 2.0f) {
            alphaF = mAlpha * 2;
        } else {
            alphaF = 255.0f * 2 -mAlpha * 2;
        }
        alpha = (int) alphaF;
        mMiddleDrawable.setBounds(mDrawableRect);
        mMiddleDrawable.setAlpha(alpha);
        mMiddleDrawable.draw(canvas);
    }

    private void drawFocusIcon(Canvas canvas) {
        int alpha = mAlpha * 2 - 255;
        if(alpha < 0) {
            alpha = 0;
        }
        mFocusDrawable.setBounds(mDrawableRect);
        mFocusDrawable.setAlpha(alpha);
        mFocusDrawable.draw(canvas);
    }

    private void drawDeFocusText(Canvas canvas) {
        mTextPaint.setColor(getCurrentTextColor());
        mTextPaint.setAlpha(255 - mAlpha);
        mTextPaint.setTextSize(getTextSize());
        canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2
                , iconHeight + iconPadding + getPaddingTop() + mFontHeight, mTextPaint);
    }

    private void drawFocusText(Canvas canvas) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(mAlpha);
        mTextPaint.setTextSize(getTextSize());
        canvas.drawText(getText().toString(), getWidth() / 2 - mTextWidth / 2
                , iconHeight + iconPadding + getPaddingTop() + mFontHeight, mTextPaint);
    }

    private void drawNotification(Canvas canvas) {
        float cx = (getWidth() - iconWidth) / 2 + iconWidth - mDotRadius;
        float cy = getPaddingTop() + mDotRadius;
        if(mNotificationCount < 0) {
            canvas.drawCircle(cx, cy, mDotRadius, mNotificationPaint);
        } else if(mNotificationCount > 0) {

        }
    }

    public void updateAlpha(float alpha) {
        mAlpha = (int) alpha;
        invalidate();
    }

    public void setRadioButtonChecked(boolean isChecked) {
        setChecked(isChecked);
        if (isChecked) {
            mAlpha = 255;
        } else {
            mAlpha = 0;
        }
        invalidate();
    }

    public static Bitmap tintBitmap(Bitmap inBitmap, int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap(inBitmap.getWidth(), inBitmap.getHeight(), inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inBitmap, 0, 0, paint);
        return outBitmap;
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        invalidate();
    }
}
