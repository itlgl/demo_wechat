package com.itlgl.demo.wechat.module.main.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by Droidroid on 2016/5/10.
 */
public class WeChatRadioGroup extends RadioGroup implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;

    public WeChatRadioGroup(Context context) {
        super(context);
    }

    public WeChatRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClickedViewChecked(position);
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(position, false);
                    }
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateGradient(position, positionOffset);
    }

    private void updateGradient(int position, float offset) {
        if (offset > 0) {
            ((WeChatRadioButton) getChildAt(position)).updateAlpha(255 * (1 - offset));
            ((WeChatRadioButton) getChildAt(position + 1)).updateAlpha(255 * offset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setSelectedViewChecked(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setSelectedViewChecked(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            ((WeChatRadioButton) getChildAt(i)).setChecked(false);
        }
        ((WeChatRadioButton) getChildAt(position)).setChecked(true);
    }

    private void setClickedViewChecked(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            ((WeChatRadioButton) getChildAt(i)).setRadioButtonChecked(false);
        }
        ((WeChatRadioButton) getChildAt(position)).setRadioButtonChecked(true);
    }

}
