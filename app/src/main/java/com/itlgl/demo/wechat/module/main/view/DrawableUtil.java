package com.itlgl.demo.wechat.module.main.view;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by ligl01 on 2017/10/13.
 */

public class DrawableUtil {
    /**
     * 更换drawable的颜色
     * 要想复制一个不影响原图片的drawable用来替换颜色，需要调用.newDrawable().mutate()方法
     * from: http://www.jianshu.com/p/8c479ed24ca8,http://www.jianshu.com/p/6bd7dd1cd491
     *
     * @param drawable
     * @param color
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, int color) {
        final Drawable drawableClone = drawable.getConstantState() == null ? drawable : drawable.getConstantState().newDrawable().mutate();
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawableClone);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
}
